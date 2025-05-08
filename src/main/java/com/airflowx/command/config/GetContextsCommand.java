package com.airflowx.command.config;

import com.airflowx.command.HelpMixin;
import com.airflowx.service.Context;
import com.airflowx.util.ContextHandler;
import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import com.github.freva.asciitable.HorizontalAlign;
import jakarta.inject.Inject;
import java.util.Map;
import picocli.CommandLine;

@CommandLine.Command(name = "get-contexts", description = "Get all saved contexts")
public class GetContextsCommand implements Runnable {

  private final ContextHandler contextHandler;
  @CommandLine.Mixin
  private HelpMixin help;

  @Inject
  public GetContextsCommand(ContextHandler contextHandler) {
    this.contextHandler = contextHandler;
  }


  @Override
  public void run() {
    Map<String, Context> contexts = contextHandler.getContexts();
    String current = contextHandler.getCurrentContextName();

    String[][] data = contexts.entrySet()
        .stream()
        .map(e -> new String[]{e.getKey() + (e.getKey().equals(current) ? "*" : ""),
            e.getValue().server().toASCIIString()})
        .toArray(String[][]::new);

    String table = AsciiTable.getTable(AsciiTable.NO_BORDERS,
        new Column[]{
            new Column().header("NAME").dataAlign(HorizontalAlign.LEFT),
            new Column().header("AIRFLOW SERVER URI").dataAlign(HorizontalAlign.LEFT)
        },
        data);

    System.out.println(table);
  }

}
