package com.airflowx.command.get;

import com.airflowx.command.HelpMixin;
import com.airflowx.dto.Dag;
import com.airflowx.dto.DagProperty;
import com.airflowx.dto.DagsInfo;
import com.airflowx.service.AirflowApi;
import com.airflowx.util.ContextHandler;
import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import com.github.freva.asciitable.HorizontalAlign;
import jakarta.inject.Inject;
import java.util.List;
import java.util.concurrent.Callable;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import picocli.CommandLine;

@CommandLine.Command(name = "dags", description = "List DAGs available in the server")
public class GetDagsCommand implements Callable<Integer> {

  private final ContextHandler contextHandler;
  @CommandLine.Mixin
  private HelpMixin help;
  @CommandLine.Option(names = {"--active"})
  private boolean onlyActive;
  @CommandLine.Option(names = {"--paused"})
  private boolean isPaused;

  @Inject
  public GetDagsCommand(ContextHandler contextHandler) {
    this.contextHandler = contextHandler;
  }

  @Override
  public Integer call() {
    Boolean onlyActiveQueryParam = null;
    Boolean isPausedQueryParam = null;
    List<String> fieldsQueryParam = DagProperty.getPropertiesList();

    AirflowApi airflowApi = RestClientBuilder.newBuilder()
        .baseUri(contextHandler.getCurrentContext().server())
        .build(AirflowApi.class);

    if (onlyActive) {
      onlyActiveQueryParam = true;
    }

    if (isPaused) {
      isPausedQueryParam = true;
    }

    DagsInfo dagsInfo = airflowApi.getDagList(onlyActiveQueryParam, isPausedQueryParam,
        fieldsQueryParam);

    String dagInfoTable = AsciiTable.builder()
        .border(AsciiTable.NO_BORDERS)
        .data(
            dagsInfo.dagList(),
            List.of(
                new Column().header("DAG ID").dataAlign(HorizontalAlign.LEFT).with(Dag::id),
                new Column().header("DAG NAME").dataAlign(HorizontalAlign.LEFT)
                    .with(Dag::displayName),
                new Column().header("ACTIVE").dataAlign(HorizontalAlign.LEFT)
                    .with(dag -> String.valueOf(dag.isActive())),
                new Column().header("PAUSED").dataAlign(HorizontalAlign.LEFT)
                    .with(dag -> String.valueOf(dag.isPaused())),
                new Column().header("SCHEDULE").dataAlign(HorizontalAlign.LEFT)
                    .with(dag -> dag.scheduleInterval().value())))
        .asString();

    System.out.println();
    System.out.println(dagInfoTable);
    System.out.println();
    System.out.println("\sTOTAL NUMBER OF ENTRIES:\s\s" + dagsInfo.dagList().size());
    System.out.println();

    return 0;
  }
}
