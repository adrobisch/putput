package org.putput.common.security;

import org.putput.api.resource.AccessTokens;
import org.putput.common.web.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AccessTokensResource extends BaseResource implements AccessTokens {

  @Autowired
  AccessTokenService accessTokenService;

  @Override
  public GetAccessTokensResponse getAccessTokens(BigDecimal page) throws Exception {
    return accessTokenService.findByUsername(user().getUsername());
  }
}
