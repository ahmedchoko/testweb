package com.wevioo.pi.auth.service.imp;

import com.wevioo.pi.auth.client.UserClient;
import com.wevioo.pi.auth.domain.model.MyUserDetails;
import com.wevioo.pi.auth.rest.dto.UserDto;
import com.wevioo.pi.auth.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
/**
 * The {@code MyUserDetailsServiceImp} class is an implementation of the {@link MyUserDetailsService} interface.
 * It provides the logic for loading user details by their username using the {@link UserClient}.
 *
 * @see org.springframework.beans.factory.annotation.Autowired
 * @see org.springframework.security.core.userdetails.UserDetails
 * @see org.springframework.security.core.userdetails.UsernameNotFoundException
 * @see org.springframework.stereotype.Service
 * @see MyUserDetailsService
 */
@Service
public class MyUserDetailsServiceImp  implements MyUserDetailsService {

    @Autowired
    private UserClient userClient;
    /**
     * Loads user details by their username using the {@link UserClient} to make a remote call.
     *
     * @param username The username of the user to load.
     * @return The {@link MyUserDetails} representing the user details.
     * @throws UsernameNotFoundException If the user is not found with the specified username.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
     try {
             UserDto user = userClient.findUserByUsername(username).getBody();
             if (user == null) {
                 throw new UsernameNotFoundException("No user with username: " + username);
             }
             return new MyUserDetails(user);
         }catch (Exception e) {
             throw new UsernameNotFoundException("user not found with username " + username);
         }
    }
}
