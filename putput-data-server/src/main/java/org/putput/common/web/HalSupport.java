package org.putput.common.web;

import org.putput.api.model.HalLink;
import org.putput.util.FileHelper;

import javax.ws.rs.core.UriInfo;

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

}
