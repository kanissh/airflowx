package com.airflowx.command.describe;

import com.airflowx.command.HelpMixin;
import com.airflowx.dto.dag.Dag;
import com.airflowx.dto.dag.Tag;
import com.airflowx.service.AirflowApi;
import com.airflowx.util.ContextHandler;
import com.airflowx.util.Strings;
import com.airflowx.util.Times;
import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import com.github.freva.asciitable.HorizontalAlign;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import picocli.CommandLine;

@CommandLine.Command(
    name = "dag",
    description = "Display information about the dag")
public class DescribeDagCommand implements Callable<Integer> {

  private final ContextHandler contextHandler;
  @CommandLine.Mixin
  private HelpMixin help;
  @CommandLine.Option(names = {"-v", "--verbose"},
      arity = "0", description = "Display detailed information")
  private boolean isVerbose;
  @CommandLine.Parameters(index = "0", description = "Dag id")
  private String dagId;

  @Inject
  public DescribeDagCommand(ContextHandler contextHandler) {
    this.contextHandler = contextHandler;
  }

  @Override
  public Integer call() {
    AirflowApi airflowApi = RestClientBuilder.newBuilder()
        .baseUri(contextHandler.getCurrentContext().server())
        .build(AirflowApi.class);

    Dag dag = airflowApi.getDagInformation(dagId);

    Column[] columns = {
        new Column().header("KEY").dataAlign(HorizontalAlign.LEFT),
        new Column().header("VALUE").dataAlign(HorizontalAlign.LEFT)
    };

    List<String[]> dataList = new ArrayList<>();

    dataList.add(new String[]{"Id", Strings.getNullSafe(dag.getId())});
    dataList.add(new String[]{"Display Name", Strings.getNullSafe(dag.getDisplayName())});
    dataList.add(new String[]{"Description", Strings.getNullSafe(dag.getDescription())});
    dataList.add(new String[]{"Root DAG ID", Strings.getNullSafe(dag.getRootDagId())});
    dataList.add(new String[]{"Owners",
        Strings.getNullSafe(dag.getOwners() != null ? String.join(",", dag.getOwners()) : null)});
    dataList.add(new String[]{"Tags", Tag.getDelimitedTagString(dag.getTags())});
    dataList.add(new String[]{"Is Paused", Strings.getNullSafe(dag.getIsPaused())});
    dataList.add(new String[]{"Is Active", Strings.getNullSafe(dag.getIsActive())});
    dataList.add(new String[]{"Is Sub DAG", Strings.getNullSafe(dag.getIsSubDag())});
    dataList.add(new String[]{"Last Parsed Time", Times.getFormattedZulu(dag.getLastParsedTime())});
    dataList.add(new String[]{"Last Pickled", Times.getFormattedZulu(dag.getLastPickled())});
    dataList.add(new String[]{"Last Expired", Times.getFormattedZulu(dag.getLastExpired())});
    dataList.add(new String[]{"Scheduler Lock", Strings.getNullSafe(dag.getSchedulerLock())});
    dataList.add(new String[]{"Pickle ID", Strings.getNullSafe(dag.getPickleId())});
    dataList.add(new String[]{"Default View", Strings.getNullSafe(dag.getDefaultView())});
    dataList.add(new String[]{"File Location", Strings.getNullSafe(dag.getFileLocation())});
    dataList.add(new String[]{"File Token", Strings.getNullSafe(dag.getFileToken())});
    dataList.add(new String[]{"Schedule Interval", Strings.getNullSafe(dag.getScheduleInterval())});
    dataList.add(new String[]{"Timetable Description",
        Strings.getNullSafe(dag.getTimetableDescription())});
    dataList.add(new String[]{"Max Active Tasks", Strings.getNullSafe(dag.getMaxActiveTasks())});
    dataList.add(new String[]{"Max Active Tasks", Strings.getNullSafe(dag.getMaxActiveTasks())});
    dataList.add(new String[]{"Max Active Runs", Strings.getNullSafe(dag.getMaxActiveRuns())});
    dataList.add(new String[]{"Has Task Concurrency Limits",
        Strings.getNullSafe(dag.getHasTaskConcurrencyLimits())});
    dataList.add(new String[]{"Has Import Errors", Strings.getNullSafe(dag.getHasImportErrors())});
    dataList.add(new String[]{"Next DAG Run", Times.getFormattedZulu(dag.getNextDagRun())});
    dataList.add(new String[]{"Next DAG Run Data Interval Start",
        Times.getFormattedZulu(dag.getNextDagRunDataIntervalStart())});
    dataList.add(new String[]{"Next DAG Run Data Interval End",
        Times.getFormattedZulu(dag.getNextDagRunDataIntervalEnd())});
    dataList.add(new String[]{"Next DAG Run Create After",
        Times.getFormattedZulu(dag.getNextDagRunCreateAfter())});
    dataList.add(new String[]{"Max Consecutive Failed DAG Runs",
        Strings.getNullSafe(dag.getMaxConsecutiveFailedDagRuns())});

    String table = AsciiTable.builder().border(AsciiTable.NO_BORDERS)
        .data(columns, dataList.toArray(new String[0][]))
        .asString();

    System.out.println(table);
    System.out.println();

    return 0;
  }

}
