package com.airflowx.command.get;

import com.airflowx.command.HelpMixin;
import picocli.CommandLine;

@CommandLine.Command(name = "get", subcommands = {GetDagsCommand.class})
public class GetCommand {

  @CommandLine.Mixin
  HelpMixin help;

}
