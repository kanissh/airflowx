package com.airflowx.dto.schedule;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
public class RelativeDelta extends ScheduleInterval {

  @JsonProperty("__type")
  private String type;
  private Integer years;
  private Integer months;
  private Integer days;
  private Integer leapdays;
  private Integer hours;
  private Integer minutes;
  private Integer seconds;
  private Integer microseconds;
  private Integer year;
  private Integer month;
  private Integer day;
  private Integer hour;
  private Integer minute;
  private Integer second;
  private Integer microsecond;

}
