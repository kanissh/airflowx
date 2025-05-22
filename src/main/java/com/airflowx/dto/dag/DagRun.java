package com.airflowx.dto.dag;

import com.airflowx.enums.DagRunState;
import com.airflowx.enums.RunType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@ToString
@Builder
public class DagRun {

  @JsonProperty("dag_run_id")
  private String dagRunId;

  @JsonProperty("dag_id")
  @JsonIgnore
  private String dagId;

  /*
    A DAG run scheduled at time T is actually responsible for processing data for the interval [T-interval, T).
    LogicalDate is the value denoted by T-interval.

    Trigger Time (When DAG run starts): 2024-03-02 00:00:00 UTC
    logical_date (Execution Date)     : 2024-03-01 00:00:00 UTC
    Covers Period                     : 2024-03-01 00:00 → 2024-03-02 00:00
  */
  @JsonProperty("logical_date")
  private OffsetDateTime logicalDate;

  @JsonProperty("start_date")
  private OffsetDateTime startDate;

  @JsonProperty("end_date")
  private OffsetDateTime endDate;

  @JsonProperty("data_interval_start")
  private OffsetDateTime dataIntervalStart;

  @JsonProperty("data_interval_end")
  private OffsetDateTime dataIntervalEnd;

  @JsonProperty("last_scheduling_decision")
  private OffsetDateTime lastSchedulingDecision;

  @JsonProperty("run_type")
  private RunType runType;

  @JsonProperty("state")
  private DagRunState state;

  @JsonProperty("external_trigger")
  private Boolean externalTrigger;

  @JsonProperty("conf")
  private Map<String, Object> conf;

  @JsonProperty("note")
  private String note;

}


