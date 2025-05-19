package com.airflowx.dto.dag;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record DagRunInfo(@JsonProperty("dag_runs") List<DagRun> dagRunList,
                         @JsonProperty("total_entries") int totalEntries) {

}
