package org.putput.common.security;

import org.putput.api.model.AccessToken;
import org.putput.api.model.AccessTokenList;
import org.putput.api.resource.AccessTokens;
import org.putput.common.web.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.putput.common.security.AccessTokenResource.accessTokenEntityToDto;

@Component
public class AccessTokensResource extends BaseResource implements AccessTokens {

  @Autowired
  AccessTokenService accessTokenService;

  @Override
  public GetAccessTokensResponse getAccessTokens(BigDecimal page) throws Exception {
    List<AccessToken> accessTokens = accessTokenService.findByUsername(user())
            .stream()
            .map(accessTokenEntityToDto())
            .collect(Collectors.toList());

    AccessTokenList tokenList = new AccessTokenList().withAccessTokens(accessTokens);

    return GetAccessTokensResponse.withHaljsonOK(tokenList);
  }

}
