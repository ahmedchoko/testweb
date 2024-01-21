package com.wevioo.pi.auth.config.oauth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;

import com.wevioo.pi.auth.common.ApplicationConstants;
import com.wevioo.pi.auth.service.MyUserDetailsService;

/**
 * The {@code AuthorizationServerConfig} class configures the Authorization Server for OAuth2 in the application.
 * It specifies the client details, token expiration times, and various security configurations.
 *
 * @see org.springframework.context.annotation.Configuration
 * @see org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
 * @see org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig  extends AuthorizationServerConfigurerAdapter {


    @Value("${security.oauth2.tokenTimeout.token}")
    private int expiration;

    @Value("${security.oauth2.tokenTimeout.refreshToken}")
    private int expirationRefreshToken;

    @Autowired
    private MyUserDetailsService  userDetailsService;

    @Autowired
    private JwtServerConfig jwtServerConfig;

    @Autowired
    private AuthenticationManager authenticationManager;


    /**
     * Configures the client details for OAuth2 in-memory clients, including client name, secret, grant types, scopes,
     * and token expiration times.
     *
     * @param clients The client details configuration.
     * @throws Exception If there are issues with client configuration.
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(ApplicationConstants.AUTH2_CLIENT)
                .secret( ApplicationConstants.SECRET)
                .authorizedGrantTypes( ApplicationConstants.PASSWORD,  ApplicationConstants.REFRESH_TOKEN)
                .scopes("read" , "write")
                .accessTokenValiditySeconds(expiration)
                .refreshTokenValiditySeconds(expirationRefreshToken);

    }
    /**
     * Configures the Authorization Server endpoints, including token store, token enhancers, authentication manager, and user details service.
     *
     * @param endpoints The Authorization Server endpoints configuration.
     * @throws Exception If there are issues with endpoint configuration.
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain
                .setTokenEnhancers(Arrays.asList(jwtServerConfig.tokenEnhancer(), jwtServerConfig.jwtTokenEnhancer()));

        endpoints.tokenStore(jwtServerConfig.tokenStore());
        endpoints.tokenEnhancer(tokenEnhancerChain);
        endpoints.authenticationManager(authenticationManager);
        endpoints.tokenGranter(tokenGranter(endpoints));
        endpoints.userDetailsService(userDetailsService);
    }
    /**
     * Configures Authorization Server security, including token key access and token access checks.
     *
     * @param oauthServer The Authorization Server security configuration.
     * @throws Exception If there are issues with security configuration.
     */

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    private TokenGranter tokenGranter(final AuthorizationServerEndpointsConfigurer endpoints) {
        List<TokenGranter> granters = new ArrayList<>();
        granters.add(new PasswordTokenGranter(endpoints, authenticationManager));
        granters.add(endpoints.getTokenGranter());
        return new CompositeTokenGranter(granters);
    }


}
