package com.wevioo.pi.auth.service;

import org.springframework.security.core.userdetails.UserDetailsService;
/**
 * The {@code MyUserDetailsService} interface extends the standard Spring Security {@link UserDetailsService}.
 * It defines a custom user service interface for loading user details based on a username.
 *
 * @see org.springframework.security.core.userdetails.UserDetailsService
 */
public interface MyUserDetailsService  extends UserDetailsService {
}
