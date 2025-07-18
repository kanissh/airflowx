package com.airflowx.dto.dag;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DagIsPaused {

  @JsonProperty("is_paused")
  boolean isPaused;
}
