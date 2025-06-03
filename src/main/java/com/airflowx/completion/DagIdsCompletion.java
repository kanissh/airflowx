package com.airflowx.completion;

import java.util.Iterator;
import java.util.List;

public class DagIdsCompletion implements Iterable<String> {

  @Override
  public Iterator<String> iterator() {
    return List.of("afx", "dag-ids-completion").iterator();
  }
}
