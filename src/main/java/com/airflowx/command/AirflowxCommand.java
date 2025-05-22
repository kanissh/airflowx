package com.airflowx.command;

import com.airflowx.command.config.ConfigCommand;
import com.airflowx.command.describe.DescribeCommand;
import com.airflowx.command.get.GetCommand;
import com.airflowx.command.set.ModifyCommand;
import com.airflowx.command.trigger.TriggerCommand;
import com.airflowx.exception.ExecutionExceptionHandler;
import io.quarkus.picocli.runtime.PicocliCommandLineFactory;
import io.quarkus.picocli.runtime.annotations.TopCommand;
import jakarta.enterprise.inject.Produces;
import org.eclipse.microprofile.config.ConfigProvider;
import picocli.CommandLine;

@TopCommand
@CommandLine.Command(name = "afx",
    versionProvider = VersionProviderWithConfigProvider.class,
    mixinStandardHelpOptions = true,
    description = "A command-line application to interact with airflow environment",
    subcommands = {
        GetCommand.class,
        ConfigCommand.class,
        DescribeCommand.class,
        TriggerCommand.class,
        ModifyCommand.class
    })
public class AirflowxCommand {

  @Produces
  CommandLine getCommandLineInstance(PicocliCommandLineFactory picocliCommandLineFactory) {
    return picocliCommandLineFactory.create()
        .setExecutionExceptionHandler(new ExecutionExceptionHandler());
  }
}

class VersionProviderWithConfigProvider implements CommandLine.IVersionProvider {

  @Override
  public String[] getVersion() {
    String applicationName = ConfigProvider.getConfig()
        .getValue("quarkus.application.name", String.class);
    String applicationVersion = ConfigProvider.getConfig()
        .getValue("quarkus.application.version", String.class);
    return new String[]{String.format("%s %s", applicationName, applicationVersion)};
  }
}
