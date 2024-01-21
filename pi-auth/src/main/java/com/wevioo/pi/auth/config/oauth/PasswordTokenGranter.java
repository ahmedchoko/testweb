package com.wevioo.pi.auth.config.oauth;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;

import com.wevioo.pi.auth.common.ApplicationConstants;
import com.wevioo.pi.auth.domain.enumeration.ProfileEnum;
import com.wevioo.pi.auth.domain.model.MyUserDetails;
import com.wevioo.pi.auth.exception.AuthentificationException;
import com.wevioo.pi.auth.utils.CommonUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PasswordTokenGranter extends AbstractTokenGranter {
	private static final String GRANT_TYPE = "password";

	private final AuthenticationManager authenticationManager;

	public PasswordTokenGranter(AuthorizationServerEndpointsConfigurer endpointsConfigurer,
			AuthenticationManager authenticationManager) {
		super(endpointsConfigurer.getTokenServices(), endpointsConfigurer.getClientDetailsService(),
				endpointsConfigurer.getOAuth2RequestFactory(), GRANT_TYPE);
		this.authenticationManager = authenticationManager;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
		Map<String, String> parameters = new LinkedHashMap<>(tokenRequest.getRequestParameters());
		String username = parameters.get("username");
		String password = parameters.get(ApplicationConstants.PASSWORD_KEY);
		String profile = parameters.get("profile");

		parameters.remove(ApplicationConstants.PASSWORD_KEY);
		Authentication userAuth = new UsernamePasswordAuthenticationToken(username, password);
		((AbstractAuthenticationToken) userAuth).setDetails(parameters);

		try {
			userAuth = this.authenticationManager.authenticate(userAuth);
		} catch (AccountStatusException e) {
			log.error(e.getMessage(), e);
			throw new AuthentificationException(e.getMessage(), "ACCESS_DENIED");
		} catch (BadCredentialsException e) {
			log.error(e.getMessage(), e);
			throw new AuthentificationException(e.getMessage(), "BAD_CREDENTIALS");
		}

		if (userAuth != null && userAuth.isAuthenticated()) {
			MyUserDetails userDetails = (MyUserDetails) userAuth.getPrincipal();
			if (Boolean.FALSE.equals(userDetails.getUser().getIsActive())) {
				throw new AuthentificationException(ApplicationConstants.MESSAGE_ERROR_M1 + username, "INACTIVE_USER");
			}
			OAuth2Request storedOAuth2Request = this.getRequestFactory().createOAuth2Request(client, tokenRequest);

			if (CommonUtil.checkEmailAddress(username)
					&& userDetails.getUser().getProfile() != ProfileEnum.valueOf(profile)) {
				throw new AuthentificationException(ApplicationConstants.MESSAGE_ERROR_M1 + username, "ACCESS_DENIED");
			}

			return new OAuth2Authentication(storedOAuth2Request, userAuth);
		} else {
			throw new AuthentificationException(ApplicationConstants.MESSAGE_ERROR_M1 + username, "BAD_CREDENTIALS");

		}
	}

}
