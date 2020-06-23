package com.social.network.userservice.config;

import com.social.network.oauth2.converter.SnAccessTokenConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import com.social.network.oauth2.config.ResourceServerConfiguration;


@Configuration
@EnableResourceServer
@EnableOAuth2Client
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class USResourceServerConfiguration extends ResourceServerConfiguration {

    public USResourceServerConfiguration(final SnAccessTokenConverter accessTokenConverter,
                                         final ResourceLoader resourceLoader) {
        super(accessTokenConverter, resourceLoader);
    }

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                .antMatchers(HttpMethod.PUT, "/roles").hasRole("SUPER_ADMIN")
                .anyRequest().authenticated();
    }
}
