package com.airflowx.command.config;

import com.airflowx.command.HelpMixin;
import com.airflowx.service.Context;
import com.airflowx.util.ContextHandler;
import jakarta.inject.Inject;
import java.net.URI;
import java.util.concurrent.Callable;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "set-context", description = "Configure the specified context with the given arguments")
public class SetContextCommand implements Callable<Integer> {

  private final ContextHandler contextHandler;
  @CommandLine.Mixin
  private HelpMixin help;
  @CommandLine.Parameters(index = "0", description = "Name of the context")
  private String contextName;
  @CommandLine.Option(names = {
      "--password"}, description = "Password for basic authentication", required = true)
  private String password;
  @CommandLine.Option(names = {
      "--server"}, description = "URL of the airflow instance", required = true)
  private String server;
  @CommandLine.Option(names = {
      "--username"}, description = "Username for basic authentication", required = true)
  private String username;

  @Inject
  public SetContextCommand(ContextHandler contextHandler) {
    this.contextHandler = contextHandler;
  }


  @Override
  public Integer call() {
    Context context = new Context(URI.create(server), username, password);
    contextHandler.setContext(contextName, context);
    System.out.println("Configured context " + contextName);

    if (!contextHandler.getCurrentContextName().equals(contextName)) {
      System.out.println("Run afx config use-context " + contextName + " to use this context");
    }

    return 0;
  }
}
