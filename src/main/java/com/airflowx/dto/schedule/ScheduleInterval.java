package com.airflowx.dto.schedule;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@JsonTypeInfo(
    use = Id.NAME,
    property = "__type",
    visible = true
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = TimeDelta.class, name = "TimeDelta"),
    @JsonSubTypes.Type(value = RelativeDelta.class, name = "RelativeDelta"),
    @JsonSubTypes.Type(value = CronExpression.class, name = "CronExpression")
})
@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
public abstract class ScheduleInterval {

}
