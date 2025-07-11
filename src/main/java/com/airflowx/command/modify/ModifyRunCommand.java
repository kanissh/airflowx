package com.airflowx.command.modify;

import com.airflowx.command.HelpMixin;
import com.airflowx.completion.DagIdsCompletion;
import com.airflowx.dto.dag.DagRun;
import com.airflowx.dto.dag.DagRunModifyState;
import com.airflowx.enums.ModifyRunState;
import com.airflowx.service.AirflowApi;
import com.airflowx.service.DagProperty;
import com.airflowx.util.ContextHandler;
import jakarta.inject.Inject;
import java.util.List;
import java.util.concurrent.Callable;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import picocli.CommandLine;

@CommandLine.Command(
    name = "run",
    description = "Modify state of the dag run")
public class ModifyRunCommand implements Callable<Integer> {

  private final ContextHandler contextHandler;
  @CommandLine.Mixin
  private HelpMixin help;
  @CommandLine.Parameters(index = "0", description = "Dag id", completionCandidates = DagIdsCompletion.class)
  private String dagId;
  @CommandLine.Option(names = {"--run"}, description = "Dag run id", required = true)
  private String dagRunId;
  @CommandLine.Option(names = {"--state"}, type = ModifyRunState.class,
      description = "State to set the dag run to", required = true)
  private ModifyRunState modifyRunState;

  @Inject
  public ModifyRunCommand(ContextHandler contextHandler) {
    this.contextHandler = contextHandler;
  }

  public ModifyRunCommand() {
    this.contextHandler = new ContextHandler();
  }

  @Override
  public Integer call() {
    List<String> fieldsQueryParam = DagProperty.getPropertiesList();

    AirflowApi airflowApi = RestClientBuilder.newBuilder()
        .baseUri(contextHandler.getCurrentContext().server())
        .build(AirflowApi.class);

    DagRun dagRun = airflowApi.modifyDagRunState(dagId, dagRunId,
        new DagRunModifyState(modifyRunState));

    System.out.println("DAG run state set to '" + dagRun.getState() + "' in");
    System.out.println("DAG ID        " + dagId);
    System.out.println("DAG RUN ID    " + dagRunId);
    System.out.println();

    return 0;
  }
}
