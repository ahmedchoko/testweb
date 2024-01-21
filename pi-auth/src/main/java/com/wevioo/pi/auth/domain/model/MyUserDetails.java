package com.wevioo.pi.auth.domain.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.wevioo.pi.auth.rest.dto.UserDto;

import lombok.Getter;
/**
 * The {@code MyUserDetails} class represents the user details for authentication and authorization within the application.
 * It implements the Spring Security {@link org.springframework.security.core.userdetails.UserDetails} interface
 * to provide essential user information and authorities.
 *
 * @see lombok.Data
 * @see lombok.Builder
 * @see lombok.NoArgsConstructor
 * @see lombok.AllArgsConstructor
 * @see org.springframework.security.core.userdetails.UserDetails
 */

public class MyUserDetails  implements UserDetails {



    /**
     * Serial Number
     */
    private static final long serialVersionUID = 1L;

    /**
     * User extra info
     */
    @Getter
    private final transient UserDto user;


    public MyUserDetails(UserDto user) {
        this.user = user;
    }

    /**
     * Returns a collection of granted authorities for the user. In this implementation, a single ROLE_NAME authority is added.
     *
     * @return A collection of granted authorities.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_NAME"));
        return  authorities;
    }

    /**
     * Retrieves the user's password.
     *
     * @return The user's password.
     */
    @Override
    public String getPassword() {
        return   this.user.getPassword();
    }

    /**
     * Retrieves the user's username (in this case, the email address).
     *
     * @return The user's username.
     */
    @Override
    public String getUsername() {
        return  this.user.getEmail();
    }

    /**
     * Indicates whether the user's account is not expired.
     *
     * @return True if the account is not expired, false otherwise.
     */
    @Override
    public boolean isAccountNonExpired() {
        return  true;
    }
    /**
     * Indicates whether the user's account is not locked.
     *
     * @return True if the account is not locked, false otherwise.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials are not expired.
     *
     * @return True if the credentials are not expired, false otherwise.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled.
     *
     * @return True if the user is enabled, false otherwise.
     */
    @Override
    public boolean isEnabled() {
        return   this.user.getIsActive();
    }
}
