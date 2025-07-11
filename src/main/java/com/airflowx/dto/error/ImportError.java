package com.airflowx.dto.error;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ImportError(
    @JsonProperty("import_error_id") int importErrorId,
    @JsonProperty("timestamp") String timestamp,
    @JsonProperty("filename") String filename,
    @JsonProperty("stack_trace") String stackTrace
) {

}