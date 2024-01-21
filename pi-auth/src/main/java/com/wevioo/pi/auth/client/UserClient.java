package com.wevioo.pi.auth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wevioo.pi.auth.rest.dto.UserDto;


/**
 * This interface defines the Feign client for making HTTP requests to the "back-api" service.
 * It provides methods for interacting with user-related endpoints.
 *
 * @see org.springframework.cloud.openfeign.FeignClient
 */
@FeignClient( name = "back-api", url =  "${feign.client.apis.piApiService}")
public interface UserClient {

    /**
     * Retrieves user details by their username from the "back-api" service.
     *
     * @param username The username of the user to retrieve.
     * @return ResponseEntity containing the user details.
     *
     * @see org.springframework.web.bind.annotation.GetMapping
     * @see org.springframework.web.bind.annotation.RequestParam
     */
    @GetMapping( "/users" )
    ResponseEntity<UserDto> findUserByUsername(@RequestParam String username );
}
