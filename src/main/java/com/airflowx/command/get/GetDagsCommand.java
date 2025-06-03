package com.airflowx.command.get;

import com.airflowx.command.HelpMixin;
import com.airflowx.completion.DagIdsCompletion;
import com.airflowx.dto.dag.Dag;
import com.airflowx.dto.dag.DagCollection;
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

@CommandLine.Command(
    name = "dag",
    aliases = {"dags"},
    description = "List DAGs available in the server")
public class GetDagsCommand implements Callable<Integer> {

  private final ContextHandler contextHandler;
  @CommandLine.Mixin
  private HelpMixin help;
  @CommandLine.Option(names = {
      "--only-active"}, arity = "0", description = "Retrieve only the active dags")
  private Boolean onlyActive;
  @CommandLine.Option(names = {
      "--paused"}, description = "Retrieve paused when --paused=true; unpaused when --paused=false; returns both when option is not set")
  private Boolean isPaused;
  @CommandLine.Option(names = {
      "--pattern-string"}, description = "Pattern string to match with dag id", completionCandidates = DagIdsCompletion.class)
  private String dagIdPatternString;

  @Inject
  public GetDagsCommand(ContextHandler contextHandler) {
    this.contextHandler = contextHandler;
  }

  public GetDagsCommand() {
    this.contextHandler = new ContextHandler();
  }

  @Override
  public Integer call() {

    AirflowApi airflowApi = RestClientBuilder.newBuilder()
        .baseUri(contextHandler.getCurrentContext().server())
        .build(AirflowApi.class);

    //setting the field list does not return total_entries field
    //keeping the parameter in the request and passing null to overcome this
    DagCollection dagCollection = airflowApi.getDagList(onlyActive, isPaused, null,
        dagIdPatternString);

    String dagInfoTable = AsciiTable.builder()
        .border(AsciiTable.NO_BORDERS)
        .data(
            dagCollection.getDagList(),
            List.of(
                new Column().header("DAG ID").dataAlign(HorizontalAlign.LEFT).with(Dag::getId),
                new Column().header("DAG NAME").dataAlign(HorizontalAlign.LEFT)
                    .with(Dag::getDisplayName),
                new Column().header("ACTIVE").dataAlign(HorizontalAlign.LEFT)
                    .with(dag -> String.valueOf(dag.getIsActive())),
                new Column().header("PAUSED").dataAlign(HorizontalAlign.LEFT)
                    .with(dag -> String.valueOf(dag.getIsPaused())),
                new Column().header("SCHEDULE").dataAlign(HorizontalAlign.LEFT)
                    .with(dag -> dag.getScheduleInterval().toString())))
        .asString();

    System.out.println();
    System.out.println(dagInfoTable);
    System.out.println();
    System.out.println("\sTOTAL NUMBER OF ENTRIES\s\s\s\s" + dagCollection.getTotalEntries());
    System.out.println();

    return 0;
  }
}
