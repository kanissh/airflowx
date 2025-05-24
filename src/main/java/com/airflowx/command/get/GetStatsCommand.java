package com.airflowx.command.get;

import com.airflowx.command.HelpMixin;
import com.airflowx.dto.dag.Dag;
import com.airflowx.dto.dag.DagCollection;
import com.airflowx.dto.stats.DagStatsCollection;
import com.airflowx.dto.stats.DagStatsItem;
import com.airflowx.enums.DagRunState;
import com.airflowx.service.AirflowApi;
import com.airflowx.util.ContextHandler;
import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import com.github.freva.asciitable.HorizontalAlign;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Objects;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import picocli.CommandLine;

@CommandLine.Command(
    name = "stats",
    description = "Get statistics of DAGs")
public class GetStatsCommand implements Runnable {

  private final ContextHandler contextHandler;
  @CommandLine.Mixin
  private HelpMixin help;
  @CommandLine.Parameters(index = "0", split = ",", description = "Comma separated list of DAG ids")
  private List<String> dagIdList;
  @CommandLine.Option(names = {"--all"}, description = "List stats for all DAGs")
  private Boolean isAll;

  @Inject
  public GetStatsCommand(ContextHandler contextHandler) {
    this.contextHandler = contextHandler;
  }

  private String getCommaSeparatedDagIds(AirflowApi airflowApi) {
    if (Objects.isNull(isAll)) {
      return String.join(",", dagIdList);
    }

    DagCollection dagCollection = airflowApi.getDagList(null, null, List.of("dag_id"), null);
    return String.join(",", dagCollection.getDagList().stream().map(Dag::getId).toList());
  }

  @Override
  public void run() {
    AirflowApi airflowApi = RestClientBuilder.newBuilder()
        .baseUri(contextHandler.getCurrentContext().server())
        .build(AirflowApi.class);

    DagStatsCollection statsCollection = airflowApi.getDagsStatistics(
        this.getCommaSeparatedDagIds(airflowApi));

    String dagInfoTable = AsciiTable.builder()
        .border(AsciiTable.NO_BORDERS)
        .data(
            statsCollection.getDagStatsItemList(),
            List.of(
                new Column().header("DAG ID").dataAlign(HorizontalAlign.LEFT)
                    .with(DagStatsItem::dagId),
                new Column().header(DagRunState.queued.name()).dataAlign(HorizontalAlign.LEFT)
                    .with(dagStatsItem -> String.valueOf(
                        dagStatsItem.statsStateItemMap().get(DagRunState.queued))),
                new Column().header(DagRunState.running.name()).dataAlign(HorizontalAlign.LEFT)
                    .with(dagStatsItem -> String.valueOf(
                        dagStatsItem.statsStateItemMap().get(DagRunState.running))),
                new Column().header(DagRunState.success.name()).dataAlign(HorizontalAlign.LEFT)
                    .with(dagStatsItem -> String.valueOf(
                        dagStatsItem.statsStateItemMap().get(DagRunState.success))),
                new Column().header(DagRunState.failed.name()).dataAlign(HorizontalAlign.LEFT)
                    .with(dagStatsItem -> String.valueOf(
                        dagStatsItem.statsStateItemMap().get(DagRunState.failed)))))
        .asString();

    System.out.println();
    System.out.println(dagInfoTable);
    System.out.println();
    System.out.println("\sTOTAL NUMBER OF ENTRIES\s\s\s\s" + statsCollection.getTotalEntries());
    System.out.println();
  }
}


