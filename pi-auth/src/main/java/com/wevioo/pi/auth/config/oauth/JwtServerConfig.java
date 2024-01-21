package com.wevioo.pi.auth.config.oauth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * The {@code JwtServerConfig} class configures and provides components for
 * handling JWT-based tokens in the application's OAuth2 setup. It defines the
 * token store, token services, JWT token enhancer, and a custom token enhancer
 * for access tokens.
 *
 * @see org.springframework.context.annotation.Configuration
 * @see org.springframework.context.annotation.Bean
 */
@Configuration
public class JwtServerConfig {
	/**
	 * 
	 */

	private static final String KEY_PASS = "wevioo@@2024++";

	/**
	 * Configures the token store for JWT tokens. In this case, an in-memory token
	 * store is used.
	 *
	 * @return An instance of the configured token store.
	 */
	@Bean
	public TokenStore tokenStore() {
				return new InMemoryTokenStore();
	}

	/**
	 * Configures the default token services, which include the token store and
	 * support for refresh tokens.
	 *
	 * @return An instance of the configured default token services.
	 */
	@Bean
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		defaultTokenServices.setSupportRefreshToken(true);
		return defaultTokenServices;
	}

	/**
	 * Configures the JWT token enhancer for enhancing JWT-based access tokens.
	 *
	 * @return An instance of the configured JWT token enhancer.
	 */
	@Bean
	protected JwtAccessTokenConverter jwtTokenEnhancer() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
	
		converter.setSigningKey(KEY_PASS);
		return converter;
	}

	/**
	 * Configures a custom token enhancer for enhancing access tokens with
	 * additional user-related information.
	 *
	 * @return An instance of the configured custom token enhancer.
	 */
	@Bean
	public TokenEnhancer tokenEnhancer() {
		return new CustomTokenEnhancer();
	}
}
