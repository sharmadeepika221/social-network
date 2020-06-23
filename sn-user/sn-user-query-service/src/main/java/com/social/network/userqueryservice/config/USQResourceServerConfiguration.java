package com.social.network.userqueryservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import com.social.network.oauth2.converter.SnAccessTokenConverter;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfiguration;

@Configuration
@EnableResourceServer
@EnableOAuth2Client
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class USQResourceServerConfiguration extends ResourceServerConfiguration {

    public USQResourceServerConfiguration(final SnAccessTokenConverter accessTokenConverter,
                                          final ResourceLoader resourceLoader) {
        //super(accessTokenConverter, resourceLoader);
    }

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/users").hasAnyRole("USER", "ADMIN", "SUPER_ADMIN")
                .anyRequest().authenticated();
    }
}
