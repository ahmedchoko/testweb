package com.wevioo.pi.auth.rest.controller;

import com.wevioo.pi.auth.common.ApplicationConstants;
import com.wevioo.pi.auth.exception.BadRequestException;
import com.wevioo.pi.auth.rest.dto.UserAuthenticationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@code AuthenticationController} class is responsible for handling
 * authentication-related operations and endpoints. It provides endpoints for
 * obtaining OAuth2 access tokens and contains a test endpoint for demonstration
 * purposes.
 **/
@Slf4j
@RestController
public class AuthenticationController {

	@Autowired
	private TokenEndpoint tokenEndpoint;

	@Autowired
	private TokenStore tokenStore;

	/**
	 * Endpoint for obtaining an OAuth2 access token. Handles different grant types,
	 * such as password and refresh token. Also supports token revocation.
	 *
	 * @param principal The authenticated principal.
	 * @param request   The user authentication request containing grant type and
	 *                  related data.
	 * @return A ResponseEntity containing the OAuth2 access token or an error
	 *         response.
	 * @throws HttpRequestMethodNotSupportedException If the HTTP request method is
	 *                                                not supported.
	 */
	@SuppressWarnings("deprecation")
	@PostMapping(value = "/oauth/token")
	public ResponseEntity<OAuth2AccessToken> getAccessToken(Principal principal,
			@RequestBody UserAuthenticationRequest request) throws HttpRequestMethodNotSupportedException {

		if (StringUtils.isEmpty(request.getGrantType())
				|| (ApplicationConstants.PASSWORD.equals(request.getGrantType())
						&& (StringUtils.isEmpty(request.getUsername()) || StringUtils.isEmpty(request.getProfile())
								|| StringUtils.isEmpty(request.getPassword())))
				|| (ApplicationConstants.REFRESH_TOKEN.equals(request.getGrantType())
						&& StringUtils.isEmpty(request.getToken()))) {
			throw new BadRequestException(ApplicationConstants.MISSING_REQUIRED_DATA, "Missing required data!");
		}

		Map<String, String> parameters = new HashMap<>();
		parameters.put(ApplicationConstants.GRANT_TYPE, request.getGrantType());
		if (ApplicationConstants.PASSWORD.equals(request.getGrantType())) {
			parameters.put(ApplicationConstants.USERNAME, request.getUsername());
			parameters.put(ApplicationConstants.PASSWORD, request.getPassword());
			parameters.put(ApplicationConstants.PROFILE, request.getProfile().toString());
		} else if (ApplicationConstants.REFRESH_TOKEN.equals(request.getGrantType())) {
			parameters.put(ApplicationConstants.REFRESH_TOKEN, request.getToken());
		} else if (ApplicationConstants.REVOKE_TOKEN.equals(request.getGrantType())) {
			OAuth2AccessToken accessToken = tokenStore.readAccessToken(request.getToken());
			if (accessToken != null) {
				log.info("--------------------------------------- logout " + accessToken.getValue());
				tokenStore.removeAccessToken(accessToken);
			}
			return ResponseEntity.status(HttpStatus.OK).body(accessToken);
		}
		return tokenEndpoint.postAccessToken(principal, parameters);

	}
}
