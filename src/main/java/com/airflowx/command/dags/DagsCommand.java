package com.airflowx.command.dags;

import com.airflowx.command.HelpMixin;
import picocli.CommandLine;

@CommandLine.Command(
    name = "dags",
    aliases = {"dag"},
    subcommands = {
        DagsListCommand.class,
        DagsStatCommand.class,
        DagsDetailsCommand.class,
        DagsListRunsCommand.class,
        DagsTriggerCommand.class,
        DagsPauseCommand.class,
        DagsUnpauseCommand.class
    },
    description = "Manage DAGs")
public class DagsCommand {

  @CommandLine.Mixin
  private HelpMixin help;

}
