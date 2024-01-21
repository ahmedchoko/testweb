package com.wevioo.pi.utility;

import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.domain.enumeration.UserTypeEnum;
import com.wevioo.pi.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author kad
 *
 */
@Component
public class SecurityUtils {
    /**
     * Inject {@link TokenStore} bean
     */
    @Autowired
    private TokenStore tokenStore;

    /**
     * Check if user is authentified
     *
     * @return true/false
     */

    public Boolean userIsAuthentified() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null;
    }

    /**
     *
     * @return current Access Token.
     */
    private OAuth2AccessToken getAccessToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new UnauthorizedException( ApplicationConstants.ERROR_USER_NOT_FOUND);
         }
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))) {
            return null;
        }

        OAuth2AuthenticationDetails authenticationDetails = (OAuth2AuthenticationDetails) authentication.getDetails();
        String token = authenticationDetails.getTokenValue();
        return tokenStore.readAccessToken(token);
    }

    /**
     * @return map containing current authenticated user informations
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> getCurrentUserInfos() {
        OAuth2AccessToken accessToken = getAccessToken();
        if (!ObjectUtils.isEmpty(accessToken)
                && !ObjectUtils.isEmpty(accessToken.getAdditionalInformation().get("userInformation"))) {
            return (Map<String, Object>) accessToken.getAdditionalInformation().get("userInformation");
        }
        return new HashMap<>();
    }

    /**
     *
     * @return List of current User Authorities : Permission_Action
     */
    @SuppressWarnings("unchecked")
    public List<String> getCurrentUserAuthorities() {
        List<String> result = new ArrayList<>();
        OAuth2AccessToken accessToken = getAccessToken();
        if (!ObjectUtils.isEmpty(accessToken)
                && !ObjectUtils.isEmpty(accessToken.getAdditionalInformation().get("authorities"))) {
            result = (List<String>) accessToken.getAdditionalInformation().get("authorities");
        }
        return result;
    }

    /**
     * Return current authenticated user email
     */
    public String getCurrentUserEmail() {
        Map<String, Object> mapUser = getCurrentUserInfos();
        if (mapUser != null)
            return (String) mapUser.get("email");
        return null;
    }

    /**
     * Return current authenticated user id
     */
    public  String getCurrentUserId() {
        Map<String, Object> mapUser = getCurrentUserInfos();
        if (mapUser != null && mapUser.containsKey("id") && mapUser.get("id")!= null ) {
            return  mapUser.get("id").toString();
        }
        return null;
    }

    /**
     * Check current user
     *
     * @param userType
     * @return true/false
     */
    public boolean checkCurrentUserIs(UserTypeEnum userType) {
        Map<String, Object> mapUser = getCurrentUserInfos();
        if (mapUser != null && mapUser.containsKey("type"))
            return userType.name().equals(mapUser.get("type"));
        return false;
    }

}
