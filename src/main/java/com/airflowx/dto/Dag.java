package com.airflowx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Dag(@JsonProperty(DagProperty.DISPLAY_NAME) String displayName,
                  @JsonProperty(DagProperty.ID) String id,
                  @JsonProperty(DagProperty.IS_ACTIVE) boolean isActive,
                  @JsonProperty(DagProperty.IS_PAUSED) boolean isPaused,
                  @JsonProperty(DagProperty.SCHEDULE_INTERVAL) ScheduleInterval scheduleInterval) {

}
