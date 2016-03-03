package org.putput.common.security;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Component
public class AccessTokenAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    private PutPutRememberMeServices rememberMeServices;
    private org.springframework.security.core.userdetails.UserDetailsService userDetailsService;

    public AccessTokenAuthenticationFilter(PutPutRememberMeServices rememberMeServices, org.springframework.security.core.userdetails.UserDetailsService userDetailsService) {
        this.rememberMeServices = rememberMeServices;
        this.userDetailsService = userDetailsService;
        setAuthenticationDetailsSource(detailsSource());
    }

    private AuthenticationDetailsSource<HttpServletRequest, ?> detailsSource() {
        return (request) -> {
            Collection<? extends GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
            return new PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails(request, authorities);
        };
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        Optional<String> accessToken = getParameterValue(request, "token");
        Optional<String> accessTokenSecret = getParameterValue(request, "secret");
        
        if (!(accessToken.isPresent() && accessTokenSecret.isPresent())) {
            return null;
        }

        String[] decodedToken = decodeAccessToken(accessToken.get());
        
        if (decodedToken.length != 3) {
            throw new IllegalArgumentException("invalid token");
        }
        
        String username = decodedToken[0];
        Long expiryTime = Long.parseLong(decodedToken[1]);
        String hash = decodedToken[2];
        
        if (tokenExpired(expiryTime)) {
            throw new IllegalArgumentException("token expired");    
        }
        
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String expectedHash = rememberMeServices.getTokenSignature(expiryTime, username, userDetails.getPassword());
        
        if (!expectedHash.equals(hash)) {
            throw new IllegalArgumentException("invalid hash");
        }
        
        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword());
    }

    private boolean tokenExpired(Long expiryTime) {
        return expiryTime < System.currentTimeMillis();
    }

    String[] decodeAccessToken(String token) {
        if(!Base64.isBase64(token.getBytes())) {
            throw new IllegalArgumentException("Access token was not Base64 encoded; value was \'" + token + "\'");
        } else {
            String base64Decoded = new String(Base64.decode(token.getBytes()));
            return StringUtils.delimitedListToStringArray(base64Decoded, ":");
        }
    }

    private Optional<String> getParameterValue(HttpServletRequest request, String name) {
        return request.getParameterMap()
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals(name))
                .findFirst()
                .flatMap(firstValue());
    }

    private Function<Map.Entry<String, String[]>, Optional<String>> firstValue() {
        return entry -> {
            if (entry.getValue().length == 0) {
                return Optional.empty();
            } else {
                return Optional.of(entry.getValue()[0]);
            }
        };
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return "N/A";
    }
}
