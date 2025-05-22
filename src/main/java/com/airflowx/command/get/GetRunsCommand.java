package com.airflowx.command.get;

import com.airflowx.command.HelpMixin;
import com.airflowx.dto.dag.DagRun;
import com.airflowx.dto.dag.DagRunInfo;
import com.airflowx.enums.DagRunState;
import com.airflowx.service.AirflowApi;
import com.airflowx.util.ContextHandler;
import com.airflowx.util.Times;
import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import com.github.freva.asciitable.HorizontalAlign;
import jakarta.inject.Inject;
import java.util.List;
import java.util.concurrent.Callable;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import picocli.CommandLine;

@CommandLine.Command(
    name = "run",
    aliases = {"runs"},
    description = "Display runs of the dag ordered by the start date of the run")
public class GetRunsCommand implements Callable<Integer> {

  private final ContextHandler contextHandler;
  @CommandLine.Mixin
  private HelpMixin help;
  @CommandLine.Parameters(index = "0", description = "Dag id")
  private String dagId;
  @CommandLine.Option(names = {"--sort-reverse"}, defaultValue = "false", arity = "0",
      description = "Order the dag runs in ascending order of the run start date. "
          + "Default ordering is descending.")
  private Boolean sortReverse;
  @CommandLine.Option(names = {"--limit"}, defaultValue = "100",
      description = "Number of items to return")
  private int limit;
  @CommandLine.Option(names = {"--state"}, type = DagRunState.class, split = ",",
      description = "Number of items to return")
  private List<String> dagRunStateList;

  @Inject
  public GetRunsCommand(ContextHandler contextHandler) {
    this.contextHandler = contextHandler;
  }

  @Override
  public Integer call() throws Exception {
    AirflowApi airflowApi = RestClientBuilder.newBuilder()
        .baseUri(contextHandler.getCurrentContext().server())
        .build(AirflowApi.class);

    DagRunInfo dagRunInfo = airflowApi.getDagRuns(dagId,
        sortReverse ? "start_date" : "-start_date", limit, dagRunStateList);

    String dagRunsInfoTable = AsciiTable.builder()
        .border(AsciiTable.NO_BORDERS)
        .data(
            dagRunInfo.dagRunList(),
            List.of(
                new Column().header("RUN ID").dataAlign(HorizontalAlign.LEFT)
                    .with(DagRun::getDagRunId),
                new Column().header("LOGICAL DATE").dataAlign(HorizontalAlign.LEFT)
                    .with(dagRun -> Times.getFormattedZulu(dagRun.getLogicalDate())),
                new Column().header("START DATE").dataAlign(HorizontalAlign.LEFT)
                    .with(dagRun -> Times.getFormattedZulu(dagRun.getStartDate())),
                new Column().header("END DATE").dataAlign(HorizontalAlign.LEFT)
                    .with(dagRun -> Times.getFormattedZulu(dagRun.getEndDate())),
                new Column().header("STATE").dataAlign(HorizontalAlign.LEFT)
                    .with(dagRun -> dagRun.getState().name()),
                new Column().header("RUN TYPE").dataAlign(HorizontalAlign.LEFT)
                    .with(dagRun -> dagRun.getRunType().name())))
        .asString();

    System.out.println("Total run entries: " + dagRunInfo.totalEntries());
    System.out.println();
    System.out.println(dagRunsInfoTable);
    System.out.println();

    return null;
  }
}
