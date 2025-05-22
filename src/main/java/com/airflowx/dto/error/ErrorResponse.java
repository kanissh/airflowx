package com.airflowx.dto.error;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ErrorResponse(@JsonProperty("status") int statusCode,
                            @JsonProperty("title") String title,
                            @JsonProperty("type") String type) {

}
