package com.airflowx.dto.stats;

import com.airflowx.enums.DagRunState;
import com.fasterxml.jackson.annotation.JsonProperty;

public record DagStatsStateItem(@JsonProperty("state") DagRunState state,
                                @JsonProperty("count") int count) {

}