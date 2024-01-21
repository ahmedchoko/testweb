package com.wevioo.pi.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * The {@code JwtServerConfig} class is responsible for configuring components related to JWT tokens in the security setup.
 * It provides bean definitions for TokenStore, DefaultTokenServices, and JwtAccessTokenConverter.
 *
 * @see org.springframework.context.annotation.Bean
 * @see org.springframework.context.annotation.Configuration
 * @see org.springframework.security.oauth2.provider.token.DefaultTokenServices
 * @see org.springframework.security.oauth2.provider.token.TokenStore
 * @see org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore
 * @see org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
 */
@Configuration
public class JwtServerConfig {

    private static final String KEY_PASS = "wevioo@@2024++";

    /**
     * Creates a bean for TokenStore. In this configuration, it uses an in-memory token store.
     *
     * @return TokenStore instance.
     */
    @Bean
    public TokenStore tokenStore() {
       return new JwtTokenStore(jwtTokenEnhancer());
    }

    /**
     * Create bean DefaultTokenServices
     *
     * @return DefaultTokenServices
     */
    @Bean
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }

    /**
     * Create bean JwtAccessTokenConverter
     *
     * @return JwtAccessTokenConverter
     */
    @Bean
    public JwtAccessTokenConverter jwtTokenEnhancer() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(KEY_PASS);
        return converter;
    }
}
