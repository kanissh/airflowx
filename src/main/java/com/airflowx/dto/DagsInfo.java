package com.airflowx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record DagsInfo(@JsonProperty("dags") List<Dag> dagList) {

}
