package com.wevioo.pi.auth.config.oauth;


import com.wevioo.pi.auth.domain.model.MyUserDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;


import java.util.LinkedHashMap;
import java.util.Map;
/**
 * The {@code CustomTokenEnhancer} class extends the JwtAccessTokenConverter to provide custom enhancements to OAuth2 access tokens.
 * It adds additional user information to the access token, such as the user's details.
 *
 * @see org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
 */
public class CustomTokenEnhancer  extends  JwtAccessTokenConverter {

    /**
     * Enhances the provided OAuth2 access token with custom information.
     *
     * @param accessToken The original OAuth2 access token.
     * @param authentication The OAuth2 authentication details.
     * @return The enhanced OAuth2 access token with additional user information.
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        Map<String, Object> additionalInfo = new LinkedHashMap<>(accessToken.getAdditionalInformation());
        // set information for user
        additionalInfo.put("userInformation", userDetails.getUser());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }
}
