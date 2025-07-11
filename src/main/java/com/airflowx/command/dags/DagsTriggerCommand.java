package com.airflowx.command.dags;

import com.airflowx.command.HelpMixin;
import com.airflowx.completion.DagIdsCompletion;
import com.airflowx.dto.dag.DagRun;
import com.airflowx.service.AirflowApi;
import com.airflowx.util.ContextHandler;
import jakarta.inject.Inject;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Callable;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import picocli.CommandLine;

@CommandLine.Command(name = "trigger", description = "Trigger new run of the dag")
public class DagsTriggerCommand implements Callable<Integer> {

  private final DateTimeFormatter dagRunIdFormat =
      DateTimeFormatter.ofPattern("dd:MM:yyyy'T'hh:mm:ss:SSXXX");
  private final ContextHandler contextHandler;
  @CommandLine.Mixin
  private HelpMixin help;
  @CommandLine.Parameters(index = "0", description = "Dag id", completionCandidates = DagIdsCompletion.class)
  private String dagId;

  @Inject
  public DagsTriggerCommand(ContextHandler contextHandler) {
    this.contextHandler = contextHandler;
  }

  public DagsTriggerCommand() {
    this.contextHandler = new ContextHandler();
  }


  @Override
  public Integer call() throws Exception {
    AirflowApi airflowApi = RestClientBuilder.newBuilder()
        .baseUri(contextHandler.getCurrentContext().server())
        .build(AirflowApi.class);

    DagRun dagRun = airflowApi.triggerNewDagRun(dagId,
        DagRun.builder().dagRunId(OffsetDateTime.now().format(dagRunIdFormat)).build());

    System.out.println("Successfully triggered a new dag run for " + dagId);
    System.out.println("DAG RUN ID    " + dagRun.getDagRunId());
    System.out.println("STATE         " + dagRun.getState());
    System.out.println();

    return null;
  }
}
