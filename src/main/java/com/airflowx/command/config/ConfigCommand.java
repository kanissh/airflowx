package com.airflowx.command.config;

import com.airflowx.command.HelpMixin;
import picocli.CommandLine;

@CommandLine.Command(
    name = "config",
    subcommands = {
        SetContextCommand.class,
        GetContextsCommand.class,
        CurrentContextCommand.class,
        UseContextCommand.class,
        RemoveContextCommand.class
    },
    description = "Set or retrieve configuration of this client")
public class ConfigCommand {

  @CommandLine.Mixin
  private HelpMixin help;

}
