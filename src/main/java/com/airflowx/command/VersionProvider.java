package com.airflowx.command;

import org.eclipse.microprofile.config.ConfigProvider;
import picocli.CommandLine;


public class VersionProvider implements CommandLine.IVersionProvider {

  @Override
  public String[] getVersion() {
    String applicationName = ConfigProvider.getConfig()
        .getValue("quarkus.application.name", String.class);
    String applicationVersion = ConfigProvider.getConfig()
        .getValue("quarkus.application.version", String.class);
    return new String[]{String.format("%s %s", applicationName, applicationVersion)};
  }
}
