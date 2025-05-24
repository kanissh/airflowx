package com.airflowx.dto.stats;

import com.airflowx.dto.CollectionInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;

@Getter
public class DagStatsCollection extends CollectionInfo {

  @JsonProperty("dags")
  private List<DagStatsItem> dagStatsItemList;

}
