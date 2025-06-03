package com.airflowx.command.config;

import com.airflowx.command.HelpMixin;
import com.airflowx.util.ContextHandler;
import jakarta.inject.Inject;
import java.util.concurrent.Callable;
import picocli.CommandLine;

@CommandLine.Command(name = "remove-context", description = "Remove context from configured contexts")
public class RemoveContextCommand implements Callable<Integer> {

  private final ContextHandler contextHandler;
  @CommandLine.Mixin
  private HelpMixin help;
  @CommandLine.Spec
  private CommandLine.Model.CommandSpec spec;
  @CommandLine.Parameters(index = "0", description = "Context name")
  private String contextName;

  @Inject
  public RemoveContextCommand(ContextHandler contextHandler) {
    this.contextHandler = contextHandler;
  }

  public RemoveContextCommand() {
    this.contextHandler = new ContextHandler();
  }

  @Override
  public Integer call() {
    if (contextHandler.getCurrentContextName().equals(contextName)) {
      spec.commandLine().getErr().println("Set another context before removing current context");
      return 1;
    }

    boolean isSuccess = contextHandler.removeContext(contextName);

    if (isSuccess) {
      spec.commandLine().getOut().println("Removed context '" + contextName + "'");
      return 0;
    }
    spec.commandLine().getErr().println("Failed to find context named '" + contextName + "'");
    return 1;
  }
}
