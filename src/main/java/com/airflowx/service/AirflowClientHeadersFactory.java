package com.airflowx.service;

import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import java.util.Base64;
import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;

public class AirflowClientHeadersFactory implements ClientHeadersFactory {

  private static String generateBasicAuthHeaderValue(String username, String password) {
    return String.format("Basic %s",
        Base64.getEncoder().encodeToString(String.format("%s:%s", username, password).getBytes()));
  }

  @Override
  public MultivaluedMap<String, String> update(MultivaluedMap<String, String> incomingHeaders,
      MultivaluedMap<String, String> clientOutgoingHeaders) {
    MultivaluedMap<String, String> result = new MultivaluedHashMap<>();

    result.add("Authorization",
        generateBasicAuthHeaderValue("admin", "admin"));
    return result;
  }
}
