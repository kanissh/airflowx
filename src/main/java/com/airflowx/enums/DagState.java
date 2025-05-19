package com.airflowx.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum DagState {
  @JsonProperty("queued")
  queued,
  @JsonProperty("running")
  running,
  @JsonProperty("success")
  success,
  @JsonProperty("failed")
  failed;
}
