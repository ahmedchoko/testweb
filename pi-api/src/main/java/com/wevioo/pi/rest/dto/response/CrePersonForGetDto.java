package com.wevioo.pi.rest.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CrePersonForGetDto {


    /**
     * lastName
     */
    private String lastName;
    /**
     * firstName
     */
    private String firstName;
    /**
     * socialReason
     */
    private String socialReason;

}
