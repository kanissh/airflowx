package com.airflowx.command.trigger;

import com.airflowx.command.HelpMixin;
import picocli.CommandLine;

@CommandLine.Command(
    name = "trigger",
    subcommands = {TriggerDagCommand.class},
    description = "Trigger new dag runs")
public class TriggerCommand {

  @CommandLine.Mixin
  private HelpMixin help;

}
