package com.airflowx.command.completion;

import com.airflowx.dto.dag.Dag;
import com.airflowx.dto.dag.DagCollection;
import com.airflowx.service.AirflowApi;
import com.airflowx.util.ContextHandler;
import jakarta.inject.Inject;
import java.util.List;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Spec;

@Command(name = "dag-ids-completion", hidden = true)
public class DagIdsCompletionCandidateCommand implements Runnable {

  private final ContextHandler contextHandler;
  @Spec
  private CommandSpec spec;

  @Inject
  public DagIdsCompletionCandidateCommand(ContextHandler contextHandler) {
    this.contextHandler = contextHandler;
  }

  public DagIdsCompletionCandidateCommand() {
    this.contextHandler = new ContextHandler();
  }

  @Override
  public void run() {
    AirflowApi airflowApi = RestClientBuilder.newBuilder()
        .baseUri(contextHandler.getCurrentContext().server())
        .build(AirflowApi.class);

    DagCollection dagCollection = airflowApi.getDagList(null, null, List.of("dag_id"), null);
    spec.commandLine().getOut().println(String.join(" ", dagCollection.getDagList().stream().map(
        Dag::getId).toList()));
  }
}
