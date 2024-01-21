package com.wevioo.pi.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
/**
 * The {@code OAuth2ResourceServerConfig} class is responsible for configuring the resource server aspects of the OAuth2 setup.
 * It specifies security rules for handling resource access and security headers.
 *
 * @see org.springframework.beans.factory.annotation.Autowired
 * @see org.springframework.context.annotation.Bean
 * @see org.springframework.context.annotation.Configuration
 * @see org.springframework.http.HttpMethod
 * @see org.springframework.security.config.annotation.web.builders.HttpSecurity
 * @see org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
 * @see org.springframework.security.crypto.password.PasswordEncoder
 * @see org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
 * @see org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
 * @see org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
 */
@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig  extends ResourceServerConfigurerAdapter {

    /**
     * Injected bean {@link JwtServerConfig}
     */
   @Autowired
   private  JwtServerConfig jwtServerConfig;


 
    /**
     * Configures the Resource Server security config, including the token store.
     *
     * @param config The resource server security configuration.
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer config) {
        config.tokenStore(jwtServerConfig.tokenStore());
    }

    /**
     * Configures the HTTP security rules for the resource server, specifying which endpoints require authentication.
     *
     * @param http The HTTP security configuration.
     * @throws Exception If there are issues with security configuration.
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().antMatcher("/**")
                // this will apply to the entire web server
                .authorizeRequests()
                .antMatchers("/users/**","/investors","/attachments/**","/attachments","/claims/public",
                		"/v3/api-docs/**", "/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config/**", "/swagger-ui/**", "/swagger-ui.html", "/v2/api-docs/**", "/configuration/ui",
						"/configuration/security",
                        "/referential/countries",
						"/webjars/**")
                .permitAll().anyRequest().authenticated();

        // set X-Frame-Options to "allow-from *"
        http.headers().frameOptions().disable().httpStrictTransportSecurity().disable()
                .addHeaderWriter(new StaticHeadersWriter("X-FRAME-OPTIONS", "ALLOW-FROM *"));
    }

    /**
     * Creates a bean for the PasswordEncoder, specifically using BCrypt for password hashing.
     *
     * @return PasswordEncoder instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
