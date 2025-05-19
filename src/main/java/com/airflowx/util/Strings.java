package com.airflowx.util;

public class Strings {

  public static boolean isBlank(String string) {
    if (string == null) {
      return true;
    }

    return string.trim().isEmpty();
  }

  public static String getNullSafe(Object value) {
    return value != null ? value.toString() : "NULL";
  }

}
