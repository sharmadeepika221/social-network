package com.social.network.authservice.oauth2;

import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import com.social.network.authservice.security.SnUserDetails;

public class SnTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(final OAuth2AccessToken accessToken,
                                     final OAuth2Authentication authentication) {
        if (authentication == null || authentication.getUserAuthentication() == null) {
            return accessToken;
        }

        final SnUserDetails snUser = (SnUserDetails) authentication.getUserAuthentication().getPrincipal();
        final Map<String, Object> additionalInfo = Map.of("user_id", snUser.getUsername());
        ((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }
}
