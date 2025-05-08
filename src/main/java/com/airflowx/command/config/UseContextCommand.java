package com.airflowx.command.config;

import com.airflowx.command.HelpMixin;
import com.airflowx.util.ContextHandler;
import jakarta.inject.Inject;
import java.util.concurrent.Callable;
import picocli.CommandLine;

@CommandLine.Command(name = "use-context", description = "Configure to use the specified context")
public class UseContextCommand implements Callable<Integer> {

  private final ContextHandler contextHandler;
  @CommandLine.Mixin
  private HelpMixin help;
  @CommandLine.Spec
  private CommandLine.Model.CommandSpec spec;
  @CommandLine.Parameters(index = "0", description = "Context name")
  private String contextName;

  @Inject
  public UseContextCommand(ContextHandler contextHandler) {
    this.contextHandler = contextHandler;
  }

  @Override
  public Integer call() {
    if (contextHandler.getCurrentContextName().equals(contextName)) {
      spec.commandLine().getOut().println("Already using context " + contextName);
      return 0;
    }

    boolean isSuccess = contextHandler.setCurrentContext(contextName);

    if (isSuccess) {
      spec.commandLine().getOut().println("Using context '" + contextName + "'");
      return 0;
    }

    spec.commandLine().getErr().println("Failed to change context");
    spec.commandLine().getErr().println("Create a context named " + contextName + " first");
    return 1;
  }
}
