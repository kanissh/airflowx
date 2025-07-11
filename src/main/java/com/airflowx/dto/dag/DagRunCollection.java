package com.airflowx.dto.dag;

import com.airflowx.dto.CollectionInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;

@Getter
public class DagRunCollection extends CollectionInfo {

  @JsonProperty("dag_runs")
  private List<DagRun> dagRunList;

}
