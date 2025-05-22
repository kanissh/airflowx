package com.airflowx.exception;

import com.airflowx.dto.error.ErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

public class AirflowResponseExceptionMapper implements ResponseExceptionMapper<RuntimeException> {

  @Override
  public RuntimeException toThrowable(Response response) {
    ErrorResponse errorResponse = response.readEntity(ErrorResponse.class);
    String message =
        "Error: " + errorResponse.title() + "\n" + "Find more info at " + errorResponse.type();
    return new AirflowResponseException(Status.fromStatusCode(errorResponse.statusCode()), message);
  }
}
