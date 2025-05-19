package com.airflowx.dto.dag;

import java.util.List;
import java.util.Objects;

public record Tag(String name) {

  public static String getDelimitedTagString(List<Tag> tagList) {
    if (tagList == null) {
      return "NULL";
    }
    List<String> tagValueList = tagList.stream().filter(Objects::nonNull).map(Tag::name).toList();
    return String.join(",", tagValueList);
  }

}
