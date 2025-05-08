package com.airflowx.util;

import com.airflowx.service.Configuration;
import com.airflowx.service.Context;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.enterprise.context.ApplicationScoped;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@ApplicationScoped
public class ContextHandler {

  private static final String CONFIG_FILE = ".airflowx";
  private final File configFile;
  private final ObjectMapper objectMapper;

  public ContextHandler() {
    this(new File(System.getProperty("user.home")));
  }

  public ContextHandler(File configDirectory) {
    this.configFile = new File(configDirectory, CONFIG_FILE);
    this.objectMapper = JsonMapper
        .builder()
        .serializationInclusion(JsonInclude.Include.NON_NULL)
        .configure(SerializationFeature.INDENT_OUTPUT, true)
        .build();
  }

  public void setContext(String contextName, Context context) {
    if (!configFile.exists()) {
      tryWriteConfiguration(
          new Configuration(contextName).addConfigurationContext(contextName, context));

      return;
    }

    Configuration configuration = tryReadConfiguration();

    tryWriteConfiguration(configuration.addConfigurationContext(contextName, context));
  }

  public Context getContext(String contextName) {
    if (!configFile.exists()) {
      warnAboutMissingConfigFile();

      return Context.defaultContext();
    }

    Configuration configuration = tryReadConfiguration();

    return configuration.configurationContexts().get(contextName);
  }

  private void warnAboutMissingConfigFile() {
    System.out.println(
        "No context has been defined, using " + Context.DEFAULT_SERVER + " by default");
    System.out.println(
        " Run 'afx config set-context <context_name> --server=<server-url> --username=<username> --password=<password>' to create a context");
  }

  public Map<String, Context> getContexts() {
    Map<String, Context> contexts = new LinkedHashMap<>();
    if (!configFile.exists()) {
      warnAboutMissingConfigFile();

      return contexts;
    }

    Configuration configuration = tryReadConfiguration();
    return configuration.configurationContexts();
  }

  public Context getCurrentContext() {
    if (!configFile.exists()) {
      warnAboutMissingConfigFile();

      return Context.defaultContext();
    }

    Configuration configuration = tryReadConfiguration();

    return configuration.configurationContexts().get(configuration.getCurrentContext());
  }

  public String getCurrentContextName() {
    if (!configFile.exists()) {
      warnAboutMissingConfigFile();

      return "";
    }

    Configuration configuration = tryReadConfiguration();

    return configuration.getCurrentContext();
  }

  public boolean setCurrentContext(String contextName) {
    if (!configFile.exists()) {
      warnAboutMissingConfigFile();
      return false;
    }

    Configuration configuration = tryReadConfiguration();

    if (!configuration.configurationContexts().containsKey(contextName)) {
      return false;
    }

    configuration.setCurrentContext(contextName);
    tryWriteConfiguration(configuration);
    return true;
  }

  public boolean removeContext(String contextName) {
    if (!configFile.exists()) {
      warnAboutMissingConfigFile();
      return false;
    }

    Configuration configuration = tryReadConfiguration();

    if (configuration.removeContext(contextName) == null) {
      return false;
    }

    tryWriteConfiguration(configuration);
    return true;
  }

  private Configuration tryReadConfiguration() {
    try {
      return objectMapper.readValue(configFile, Configuration.class);
    } catch (IOException e) {
      throw new RuntimeException(
          "Couldn't read configuration file ~/" + CONFIG_FILE +
              "running 'afx config set-context <context_name>", e);
    }
  }

  private void tryWriteConfiguration(Configuration configuration) {
    try {
      objectMapper.writeValue(configFile, configuration);
    } catch (IOException e) {
      throw new RuntimeException(
          "Couldn't write configuration file " + configFile +
              "running 'afx config set-context <context_name>", e);
    }
  }
}
