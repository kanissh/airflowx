package com.airflowx.command.describe;

import com.airflowx.command.HelpMixin;
import picocli.CommandLine;

@CommandLine.Command(
    name = "describe",
    subcommands = {DescribeDagCommand.class},
    description = "Display information about dags, tasks")
public class DescribeCommand {

  @CommandLine.Mixin
  private HelpMixin help;

}
