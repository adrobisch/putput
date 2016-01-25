package org.putput.common.web;

import org.putput.api.model.HalLink;
import org.putput.util.FileHelper;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.Map;

public interface HalSupport {

  UriInfo getUriInfo();

  default String getProtocol() {
    return "http";
  }

  default HalLink link(Class<?> targetResource, String... paths) {
    return new HalLink().withHref(getUriInfo().getBaseUriBuilder().segment(FileHelper.getPathFromResource(targetResource))
        .scheme(getProtocol())
        .segment(paths).toTemplate());
  }

  default HalLink link(Class<?> targetResource, Map<String, String> queryParams, String... paths) {
    return new HalLink().withHref(withParams(getUriInfo().getBaseUriBuilder(), queryParams)
        .segment(FileHelper.getPathFromResource(targetResource))
        .scheme(getProtocol())
        .segment(paths).toTemplate());
  }

  default UriBuilder withParams(UriBuilder baseUriBuilder, Map<String, String> queryParams) {
    queryParams.entrySet().forEach(entry -> baseUriBuilder.queryParam(entry.getKey(), entry.getValue()));
    return baseUriBuilder;
  }

}
