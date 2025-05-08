package com.airflowx.service;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.LinkedHashMap;
import java.util.Map;

public class Configuration {

  private final Map<String, Context> configurationContexts = new LinkedHashMap<>();
  private String currentContext;

  public Configuration(@JsonProperty("currentContext") String currentContext) {
    this.currentContext = currentContext;
  }

  public String getCurrentContext() {
    return currentContext;
  }

  public void setCurrentContext(String currentContext) {
    this.currentContext = currentContext;
  }

  public Context removeContext(String context) {
    return configurationContexts.remove(context);
  }

  @JsonAnyGetter
  public Map<String, Context> configurationContexts() {
    return configurationContexts;
  }

  @JsonAnySetter
  public Configuration addConfigurationContext(String contextName, Context configuration) {
    configurationContexts.put(contextName, configuration);
    return this;
  }
}
