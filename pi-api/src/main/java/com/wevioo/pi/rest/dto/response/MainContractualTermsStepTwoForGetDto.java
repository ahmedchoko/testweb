package com.wevioo.pi.rest.dto.response;


import java.io.Serializable;
import java.util.Date;

import com.wevioo.pi.rest.dto.UserDto;
import com.wevioo.pi.rest.dto.request.MainContractualTermsStepTwoBaseDto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MainContractualTermsStepTwoForGetDto extends MainContractualTermsStepTwoBaseDto implements Serializable {



    /**
     * Serial Number
     */
    private static final long serialVersionUID = -7139054719695454187L;


    /**
     * id
     */
    private String id;

    /**
     * Modification date.
     */
    private Date modificationDate;


    /**
     * Modified by.
     */
    private UserDto modifiedBy;

    /**
     * creationDate

     */
    private Date creationDate;

    /**
     * Created By
     */
    private UserDto createdBy;

}
