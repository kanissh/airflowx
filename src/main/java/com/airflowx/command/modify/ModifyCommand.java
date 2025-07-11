package com.airflowx.command.modify;

import com.airflowx.command.HelpMixin;
import picocli.CommandLine;

@CommandLine.Command(
    name = "modify",
    subcommands = {ModifyRunCommand.class},
    description = "Modify dag runs in the airflow server")
public class ModifyCommand {

  @CommandLine.Mixin
  private HelpMixin help;

}
