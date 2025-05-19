package com.airflowx.service;

import java.util.List;

public interface DagProperty {

  String DISPLAY_NAME = "dag_display_name";
  String ID = "dag_id";
  String IS_ACTIVE = "is_active";
  String IS_PAUSED = "is_paused";
  String SCHEDULE_INTERVAL = "schedule_interval";

  static List<String> getPropertiesList() {
    return List.of(DISPLAY_NAME, ID, IS_ACTIVE, IS_PAUSED, SCHEDULE_INTERVAL);
  }
}
