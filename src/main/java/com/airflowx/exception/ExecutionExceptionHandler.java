package com.airflowx.exception;

import picocli.CommandLine;
import picocli.CommandLine.ExitCode;
import picocli.CommandLine.IExecutionExceptionHandler;
import picocli.CommandLine.ParseResult;

public class ExecutionExceptionHandler implements IExecutionExceptionHandler {

  @Override
  public int handleExecutionException(Exception ex, CommandLine commandLine,
      ParseResult fullParseResult) throws Exception {
    System.err.println(ex.getMessage());
    return ExitCode.USAGE;
  }
}
