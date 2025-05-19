package com.airflowx.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum RunType {
  @JsonProperty("backfill")
  backfill,
  @JsonProperty("manual")
  manual,
  @JsonProperty("scheduled")
  scheduled,
  @JsonProperty("dataset_triggered")
  dataset_triggered;
}

