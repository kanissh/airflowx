package com.airflowx.service;

import com.airflowx.util.Strings;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URI;

public record Context(URI server, String username, String password) {

  public static final String DEFAULT_SERVER = "http://localhost:8080";

  public Context(
      @JsonProperty("server") URI server,
      @JsonProperty("username") String username,
      @JsonProperty("password") String password) {
    this.server = server;
    this.username = username;
    this.password = password;
  }

  public static Context defaultContext() {
    return new Context(URI.create(DEFAULT_SERVER), null, null);
  }

  @JsonIgnore
  public boolean isUsingBasicAuthentication() {
    return !Strings.isBlank(this.username()) &&
        !Strings.isBlank(this.password());
  }
}
