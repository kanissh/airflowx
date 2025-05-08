package com.airflowx.util;

public class Strings {

  public static boolean isBlank(String string) {
    if (string == null) {
      return true;
    }

    return string.trim().isEmpty();
  }

}
