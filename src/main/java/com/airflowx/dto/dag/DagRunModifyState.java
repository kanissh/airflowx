package com.airflowx.dto.dag;

import com.airflowx.enums.ModifyRunState;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DagRunModifyState {

  @JsonProperty("state")
  ModifyRunState state;
}
