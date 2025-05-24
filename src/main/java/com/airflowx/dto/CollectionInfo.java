package com.airflowx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CollectionInfo {

  @JsonProperty("total_entries")
  protected int totalEntries;

}
