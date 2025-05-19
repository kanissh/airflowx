package com.airflowx.dto.dag;

import com.airflowx.dto.schedule.ScheduleInterval;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Dag {

  @JsonProperty("dag_id")
  private String id;

  @JsonProperty("dag_display_name")
  private String displayName;

  @JsonProperty("root_dag_id")
  private String rootDagId;

  @JsonProperty("is_paused")
  private Boolean isPaused;

  @JsonProperty("is_active")
  private Boolean isActive;

  @JsonProperty("is_subdag")
  private Boolean isSubDag;

  @JsonProperty("last_parsed_time")
  private OffsetDateTime lastParsedTime;

  @JsonProperty("last_pickled")
  private OffsetDateTime lastPickled;

  @JsonProperty("last_expired")
  private OffsetDateTime lastExpired;

  @JsonProperty("scheduler_lock")
  private Boolean schedulerLock;

  @JsonProperty("pickle_id")
  private String pickleId;

  @JsonProperty("default_view")
  private String defaultView;

  @JsonProperty("fileloc")
  private String fileLocation;

  @JsonProperty("file_token")
  private String fileToken;

  @JsonProperty("owners")
  private List<String> owners;

  @JsonProperty("description")
  private String description;

  @JsonProperty("schedule_interval")
  private ScheduleInterval scheduleInterval;

  @JsonProperty("timetable_description")
  private String timetableDescription;

  @JsonProperty("tags")
  private List<Tag> tags;

  @JsonProperty("max_active_tasks")
  private Integer maxActiveTasks;

  @JsonProperty("max_active_runs")
  private Integer maxActiveRuns;

  @JsonProperty("has_task_concurrency_limits")
  private Boolean hasTaskConcurrencyLimits;

  @JsonProperty("has_import_errors")
  private Boolean hasImportErrors;

  @JsonProperty("next_dagrun")
  private OffsetDateTime nextDagRun;

  @JsonProperty("next_dagrun_data_interval_start")
  private OffsetDateTime nextDagRunDataIntervalStart;

  @JsonProperty("next_dagrun_data_interval_end")
  private OffsetDateTime nextDagRunDataIntervalEnd;

  @JsonProperty("next_dagrun_create_after")
  private OffsetDateTime nextDagRunCreateAfter;

  @JsonProperty("max_consecutive_failed_dag_runs")
  private Integer maxConsecutiveFailedDagRuns;
}
