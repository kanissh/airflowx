package com.airflowx.command.config;

import com.airflowx.command.HelpMixin;
import com.airflowx.service.Context;
import com.airflowx.util.ContextHandler;
import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import com.github.freva.asciitable.HorizontalAlign;
import jakarta.inject.Inject;
import picocli.CommandLine;

@CommandLine.Command(name = "current-context", description = "Display the current context")
public class CurrentContextCommand implements Runnable {

  private final ContextHandler contextHandler;
  @CommandLine.Mixin
  private HelpMixin help;

  @Inject
  public CurrentContextCommand(ContextHandler contextHandler) {
    this.contextHandler = contextHandler;
  }

  public CurrentContextCommand() {
    this.contextHandler = new ContextHandler();
  }

  @Override
  public void run() {
    Context currentContext = contextHandler.getCurrentContext();
    String[][] data = new String[3][];
    data[0] = new String[]{"Airflow server URI", currentContext.server().toASCIIString()};
    data[1] = new String[]{"User", currentContext.username()};
    data[2] = new String[]{"Password", currentContext.password() == null ? "" : "********"};

    String output = AsciiTable.builder()
        .border(AsciiTable.NO_BORDERS)
        .data(
            new Column[]{
                new Column().header("KEY").dataAlign(HorizontalAlign.LEFT),
                new Column().header("VALUE").dataAlign(HorizontalAlign.LEFT)
            }, data)
        .asString();

    System.out.println("Using context: " + contextHandler.getCurrentContextName());
    System.out.println();
    System.out.println(output);
    System.out.println();
  }
}
