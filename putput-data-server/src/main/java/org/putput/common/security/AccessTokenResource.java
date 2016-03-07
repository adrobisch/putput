package org.putput.common.security;

import org.putput.common.web.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

@Component
public class AccessTokenResource extends BaseResource implements org.putput.api.resource.AccessToken {
  
  @Autowired
  AccessTokenService accessTokenService;
  
  @Override
  public PostAccessTokenResponse postAccessToken(org.putput.api.model.AccessToken entity) throws Exception {
    AccessTokenEntity accessTokenEntity = accessTokenService.createAccessToken(user(), entity.getClientId(), entity.getExpiryDate().longValue());
    return PostAccessTokenResponse.withCreated(link(org.putput.api.resource.AccessToken.class, accessTokenEntity.getId()).getHref());
  }

  @Override
  public GetAccessTokenByIdResponse getAccessTokenById(String id) throws Exception {
    Optional<AccessTokenEntity> byUsernameAndId = accessTokenService.findByUsernameAndId(user(), id);
    
    return byUsernameAndId
            .map(accessTokenEntityToDto())
            .map(GetAccessTokenByIdResponse::withHaljsonOK)
            .orElseGet(GetAccessTokenByIdResponse::withNotFound);
  }

  @Override
  public PutAccessTokenByIdResponse putAccessTokenById(String id, org.putput.api.model.AccessToken entity) throws Exception {
    return accessTokenService
            .update(user(), entity)
            .map(accessTokenEntity -> PutAccessTokenByIdResponse.withOK())
            .orElseGet(PutAccessTokenByIdResponse::withNotFound);
  }

  @Override
  public DeleteAccessTokenByIdResponse deleteAccessTokenById(String id) throws Exception {
    Optional<AccessTokenEntity> byUsernameAndId = accessTokenService.findByUsernameAndId(user(), id);
    return byUsernameAndId
            .map(accessTokenEntity -> {
              accessTokenService.deleteToken(accessTokenEntity.getId());
              return true;
            }).map(result -> DeleteAccessTokenByIdResponse.withOK())
            .orElseGet(DeleteAccessTokenByIdResponse::withNotFound);
  }

  public static Function<AccessTokenEntity, org.putput.api.model.AccessToken> accessTokenEntityToDto() {
    return accessTokenEntity -> new org.putput.api.model.AccessToken()
            .withId(accessTokenEntity.getId())
            .withCreated(accessTokenEntity.getCreated().doubleValue())
            .withClientId(accessTokenEntity.getClientId())
            .withToken(accessTokenEntity.getToken())
            .withExpiryDate(accessTokenEntity.getExpiryDate().doubleValue())
            .withSecret(accessTokenEntity.getSecret());
  }
}
