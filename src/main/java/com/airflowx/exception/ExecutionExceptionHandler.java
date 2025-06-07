package com.airflowx.exception;

import picocli.CommandLine;
import picocli.CommandLine.ExitCode;
import picocli.CommandLine.IExecutionExceptionHandler;
import picocli.CommandLine.ParseResult;

public class ExecutionExceptionHandler implements IExecutionExceptionHandler {

  @Override
  public int handleExecutionException(Exception ex, CommandLine commandLine,
      ParseResult fullParseResult) throws Exception {

    if (ex instanceof jakarta.ws.rs.ProcessingException) {
      System.err.println(
          "Failed to connect to the airflow instance. " + ex.getCause().getMessage());
    } else {
      System.err.println(ex.getCause().getMessage());
    }
    return ExitCode.USAGE;
  }
}
