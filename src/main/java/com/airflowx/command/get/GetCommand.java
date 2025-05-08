package com.airflowx.command.get;

import com.airflowx.command.HelpMixin;
import picocli.CommandLine;

@CommandLine.Command(
    name = "get",
    subcommands = {GetDagsCommand.class},
    description = "Display information about DAGs")
public class GetCommand {

  @CommandLine.Mixin
  private HelpMixin help;

}
