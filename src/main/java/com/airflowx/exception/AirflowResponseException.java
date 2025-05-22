package com.airflowx.exception;

import jakarta.ws.rs.core.Response.Status;

public class AirflowResponseException extends RuntimeException {

  public AirflowResponseException(Status httpStatus, String message) {
    super(message);
  }
}
