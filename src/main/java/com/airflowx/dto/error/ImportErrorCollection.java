package com.airflowx.dto.error;

import com.airflowx.dto.CollectionInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;

@Getter
public class ImportErrorCollection extends CollectionInfo {

  @JsonProperty("import_errors")
  private List<ImportError> importErrorList;
}
