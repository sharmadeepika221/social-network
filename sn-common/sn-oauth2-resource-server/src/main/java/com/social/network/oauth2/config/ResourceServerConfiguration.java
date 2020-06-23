package com.social.network.oauth2.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.social.network.oauth2.converter.SnAccessTokenConverter;
import com.social.network.oauth2.handler.SnAccessDeniedHandler;
import com.social.network.oauth2.handler.SnAuthenticationEntryPoint;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

public abstract class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private final SnAccessTokenConverter accessTokenConverter;
    private final ResourceLoader resourceLoader;

    @Value("${security.oauth2.path.public_key}")
    private String publicKeyPath;

    public ResourceServerConfiguration(final SnAccessTokenConverter accessTokenConverter,
                                       final ResourceLoader resourceLoader) {
        this.accessTokenConverter = accessTokenConverter;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void configure(final ResourceServerSecurityConfigurer resources) throws IOException {
        resources.tokenServices(tokenServices())
                .authenticationEntryPoint(new SnAuthenticationEntryPoint())
                .accessDeniedHandler(new SnAccessDeniedHandler());
    }

    @Bean
    public TokenStore tokenStore() throws IOException {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() throws IOException {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setAccessTokenConverter(accessTokenConverter);
        converter.setVerifierKey(getVerifierKey());
        return converter;
    }

    @Bean
    public DefaultTokenServices tokenServices() throws IOException {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }

    private String getVerifierKey() throws IOException {
        final Resource publicKeyResource = resourceLoader.getResource(publicKeyPath);
        return IOUtils.toString(publicKeyResource.getInputStream(), StandardCharsets.UTF_8);
    }
}
