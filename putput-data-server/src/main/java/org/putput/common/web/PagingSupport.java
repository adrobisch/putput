package org.putput.common.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.Map;

import static java.util.Optional.ofNullable;

public interface PagingSupport {
  default Pageable pageable(Number page) {
    return new PageRequest(ofNullable(page).orElse(0).intValue(), pageSize());
  }

  default int pageSize() {
    return 15;
  }

  default Map<String, String> nextPageParams(Page<?> streamItemPage) {
    Map<String, String> params = new HashMap<>();
    params.put("page", "" + streamItemPage.nextPageable().getPageNumber());
    return params;
  }

  default Map<String, String> previousPageParams(Page<?> streamItemPage) {
    Map<String, String> params = new HashMap<>();
    params.put("page", "" + streamItemPage.previousPageable().getPageNumber());
    return params;
  }
}
