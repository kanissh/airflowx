package com.airflowx.command.get;

import com.airflowx.command.HelpMixin;
import picocli.CommandLine;

@CommandLine.Command(
    name = "get",
    subcommands = {GetDagsCommand.class, GetRunsCommand.class, GetStatsCommand.class},
    description = "Display DAGs, tasks in the airflow server")
public class GetCommand {

  @CommandLine.Mixin
  private HelpMixin help;

}
