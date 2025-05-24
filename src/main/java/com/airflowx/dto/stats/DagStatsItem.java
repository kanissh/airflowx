package com.airflowx.dto.stats;

import com.airflowx.enums.DagRunState;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record DagStatsItem(@JsonProperty("dag_id") String dagId,
                           @JsonProperty("stats") List<DagStatsStateItem> statsStateItemList,
                           Map<DagRunState, Integer> statsStateItemMap) {

  @JsonCreator
  public DagStatsItem(@JsonProperty("dag_id") String dagId,
      @JsonProperty("stats") List<DagStatsStateItem> statsStateItemList) {
    this(dagId, statsStateItemList, statsStateItemList.stream()
        .collect(Collectors.toMap(DagStatsStateItem::state, DagStatsStateItem::count)));
  }
}
