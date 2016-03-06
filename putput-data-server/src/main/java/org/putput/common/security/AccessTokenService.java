package org.putput.common.security;

import org.apache.commons.codec.digest.DigestUtils;
import org.putput.common.UuidService;
import org.putput.users.UserEntity;
import org.putput.users.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
public class AccessTokenService {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  Environment environment;

  @Autowired
  UserDetailsService userDetailsService;
  
  @Autowired
  UserRepository userRepository;

  @Autowired
  AccessTokenRepository accessTokenRepository;
  
  @Autowired
  UuidService uuidService;

  public List<AccessTokenEntity> findByUsername(String username) {
    return accessTokenRepository
        .findByOwner(username, new PageRequest(0, Integer.MAX_VALUE))
        .getContent();
  }

  public Optional<AccessTokenEntity> findByUsernameAndId(String username, String id) {
    return Optional.ofNullable(accessTokenRepository
            .findByOwnerAndId(username, id));
  }
  
  public void deleteToken(String id) {
    accessTokenRepository.delete(id);
  }

  public Optional<AccessToken> validateToken(String accessToken, String secret) {
    String[] decodedToken = decodeAccessToken(accessToken);

    String username = decodedToken[0];
    Long expiryTime = Long.parseLong(decodedToken[1]);
    String hash = decodedToken[2];

    Optional<AccessTokenEntity> storedToken = findToken(accessToken, secret);

    if (!storedToken.isPresent()) {
      logger.warn("unknown token");
      return Optional.empty();
    }

    if (decodedToken.length != 3) {
      logger.warn("invalid token");
      return Optional.empty();
    }

    if (tokenExpired(expiryTime)) {
      logger.warn("token expired");
      return Optional.empty();
    }

    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    String expectedHash = createHash(expiryTime, username, userDetails.getPassword());

    if (!expectedHash.equals(hash)) {
      logger.warn("invalid hash");
      return Optional.empty();
    }

    return Optional.of(new AccessToken(username, expiryTime, hash));
  }

  public Optional<AccessTokenEntity> findToken(String accessToken, String secret) {
    return Optional.of(accessTokenRepository.findByTokenAndSecret(accessToken, secret));
  }

  String[] decodeAccessToken(String token) {
    if(!Base64.isBase64(token.getBytes())) {
      throw new IllegalArgumentException("Access token was not Base64 encoded; value was \'" + token + "\'");
    } else {
      String base64Decoded = new String(Base64.decode(token.getBytes()));
      return StringUtils.delimitedListToStringArray(base64Decoded, ":");
    }
  }

  private String createHash(Long expiryTime, String username, String password) {
    String data = username + ":" + expiryTime + ":" + password + ":" + getSalt();
    return md5Hex(data.getBytes());
  }

  private String getSalt() {
    return environment.getProperty("access.token.salt", "changeme");
  }

  private String md5Hex(byte[] input) {
    try {
      return new String(Hex.encode(MessageDigest.getInstance("MD5").digest(input)));
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException(e);
    }
  }

  private boolean tokenExpired(Long expiryTime) {
    return expiryTime < System.currentTimeMillis();
  }

  public AccessTokenEntity createAccessToken(String username, String clientId, long expiryDate) {
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    UserEntity userEntity = userRepository.findByUsername(username);
    
    AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
    accessTokenEntity.setId(uuidService.uuid());
    accessTokenEntity.setClientId(clientId);
    accessTokenEntity.setOwner(userEntity);
    accessTokenEntity.setToken(createToken(userDetails, expiryDate));
    accessTokenEntity.setSecret(DigestUtils.sha256Hex(uuidService.uuid()));
    accessTokenEntity.setExpiryDate(expiryDate);
    
    return accessTokenRepository.save(accessTokenEntity);
  }

  String createToken(UserDetails userDetails, long expiryDate) {
    String hash = createHash(expiryDate, userDetails.getUsername(), userDetails.getPassword());
    return new String(Base64.encode(String.format("%s:%s:%s", userDetails.getUsername(), "" + expiryDate, hash).getBytes()));
  }
  
  public Optional<AccessTokenEntity> update(String username, org.putput.api.model.AccessToken entity) {
    return findByUsernameAndId(username, entity.getId())
            .map(tokenEntity -> tokenEntity.setClientId(entity.getClientId()).setExpiryDate(entity.getExpiryDate().longValue()))
            .map(accessTokenRepository::save);
  }
}
