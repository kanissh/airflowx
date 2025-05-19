package com.airflowx.util;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class Times {

  private static final DateTimeFormatter DATE_TIME_WITH_ZONE_FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss XXX");

  public static String getFormattedZulu(OffsetDateTime offsetDateTime) {
    return offsetDateTime != null ? offsetDateTime.format(DATE_TIME_WITH_ZONE_FORMATTER) : "NULL";
  }

}
