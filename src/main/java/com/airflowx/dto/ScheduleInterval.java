package com.airflowx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ScheduleInterval(@JsonProperty("__type") String type, String value) {

}