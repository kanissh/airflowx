package com.airflowx.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ModifyRunState {
  //    Queued is not a terminal state but it is a valid API request value
//  @JsonProperty("queued")
//  queued,
  @JsonProperty("success")
  success,
  @JsonProperty("failed")
  failed;

}
