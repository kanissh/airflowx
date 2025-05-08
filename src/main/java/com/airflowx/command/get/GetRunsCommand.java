package com.airflowx.command.get;

import com.airflowx.command.HelpMixin;
import java.util.concurrent.Callable;
import picocli.CommandLine;
import picocli.CommandLine.Parameters;

@CommandLine.Command(name = "runs")
public class GetRunsCommand implements Callable<Integer> {

  @CommandLine.Mixin
  private HelpMixin help;
  @CommandLine.Spec
  private CommandLine.Model.CommandSpec spec;
  @Parameters(index = "0", description = "Dag id")
  private String dagId;

  @Override
  public Integer call() throws Exception {

    return null;
  }
}
