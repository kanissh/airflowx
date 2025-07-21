package com.airflowx.command;

import com.airflowx.command.completion.DagIdsCompletionCandidateCommand;
import com.airflowx.command.config.ConfigCommand;
import com.airflowx.command.dags.DagsCommand;
import com.airflowx.command.importerror.ImportErrorsCommand;
import com.airflowx.command.modify.ModifyCommand;
import com.airflowx.exception.ExecutionExceptionHandler;
import io.quarkus.picocli.runtime.PicocliCommandLineFactory;
import io.quarkus.picocli.runtime.annotations.TopCommand;
import jakarta.enterprise.inject.Produces;
import picocli.CommandLine;

@TopCommand
@CommandLine.Command(name = "afx",
    versionProvider = VersionProvider.class,
    mixinStandardHelpOptions = true,
    description = "CLI application to interact with airflow via the stable REST API",
    subcommands = {
        DagsCommand.class,
        ImportErrorsCommand.class,
        ConfigCommand.class,
        ModifyCommand.class,
        DagIdsCompletionCandidateCommand.class
    })
public class AirflowxCommand {

  @Produces
  CommandLine getCommandLineInstance(PicocliCommandLineFactory picocliCommandLineFactory) {
    return picocliCommandLineFactory.create()
        .setExecutionExceptionHandler(new ExecutionExceptionHandler());
  }
}
