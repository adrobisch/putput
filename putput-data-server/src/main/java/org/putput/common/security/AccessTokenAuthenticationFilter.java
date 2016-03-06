package org.putput.common.security;


import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Component
public class AccessTokenAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {
    private AccessTokenService accessTokenService;

    public AccessTokenAuthenticationFilter(AccessTokenService accessTokenService) {
        this.accessTokenService = accessTokenService;

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
        Optional<String> accessToken = getSingleParameterValue(request, "access_token");
        Optional<String> accessTokenSecret = getSingleParameterValue(request, "secret");

        if (accessToken.isPresent() && accessTokenSecret.isPresent()) {
            return getAuthentication(accessToken.get(), accessTokenSecret.get());
        } else {
            return null;
        }
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String accessToken, String secret) {
        Optional<AccessToken> validToken = accessTokenService.validateToken(accessToken, secret);
        if (validToken.isPresent()) {
            return new UsernamePasswordAuthenticationToken(validToken.get().getUsername(), "N/A");
        } else {
            return null;
        }
    }

    private Optional<String> getSingleParameterValue(HttpServletRequest request, String name) {
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
