package com.airflowx.command.dags;

import com.airflowx.command.HelpMixin;
import com.airflowx.completion.DagIdsCompletion;
import com.airflowx.dto.dag.Dag;
import com.airflowx.dto.dag.DagIsPaused;
import com.airflowx.service.AirflowApi;
import com.airflowx.util.ContextHandler;
import jakarta.inject.Inject;
import java.util.Objects;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import picocli.CommandLine;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Spec;

@CommandLine.Command(name = "unpause", description = "Unpause DAG/DAGs, order of priority is --all, --pattern, --dag-id")
public class DagsUnpauseCommand implements Runnable {

  private final ContextHandler contextHandler;

  @Spec
  CommandSpec spec;

  @CommandLine.Mixin
  private HelpMixin help;

  @CommandLine.Option(names = {
      "--dag-id"}, description = "DAG id", completionCandidates = DagIdsCompletion.class)
  private String dagId;

  @CommandLine.Option(names = {"--all"}, description = "Pause all DAGs", defaultValue = "false")
  private boolean isAll;

  @CommandLine.Option(names = {"--pattern"}, description = "Pause matching DAG id pattern")
  private String dagIdPattern;

  @Inject
  public DagsUnpauseCommand(ContextHandler contextHandler) {
    this.contextHandler = contextHandler;
  }

  public DagsUnpauseCommand() {
    this.contextHandler = new ContextHandler();
  }

  @Override
  public void run() {
    StringBuilder outputStringBuilder = new StringBuilder();

    AirflowApi airflowApi = RestClientBuilder.newBuilder()
        .baseUri(contextHandler.getCurrentContext().server())
        .build(AirflowApi.class);

    if (Objects.nonNull(dagIdPattern) || isAll) {
      outputStringBuilder.append(unpauseAllDags(airflowApi, dagIdPattern, isAll));
    } else {
      outputStringBuilder.append(unpauseDag(airflowApi, dagId));
    }

    System.out.println();
    System.out.println(outputStringBuilder);
    System.out.println();
  }

  private String unpauseDag(AirflowApi airflowApi, String dagId) {
    if (dagId == null) {
      spec.commandLine().usage(System.err);
    }
    Dag dag = airflowApi.updateDagPauseState(dagId, new DagIsPaused(false));
    return dagId + " DAG successfully unpaused";
  }

  private String unpauseAllDags(AirflowApi airflowApi, String dagIdPattern, boolean isAll) {
    int totalEntries = airflowApi.updateAllDagsPauseState(isAll ? "~" : dagIdPattern,
        new DagIsPaused(false)).getTotalEntries();
    return "Successfully unpaused " + totalEntries + " DAGs";
  }
}