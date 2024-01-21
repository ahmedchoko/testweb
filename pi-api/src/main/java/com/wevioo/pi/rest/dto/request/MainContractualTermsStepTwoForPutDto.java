package com.wevioo.pi.rest.dto.request;

import com.wevioo.pi.rest.dto.UserDto;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;


@Getter
@Setter
public class MainContractualTermsStepTwoForPutDto extends MainContractualTermsStepTwoBaseDto implements Serializable {


    /**
     * Serial Number
     */
    private static final long serialVersionUID = -7139054719695454181L;


    /**
     * creationDate

     */
    private Date creationDate;

    /**
     * Created By
     */
    private UserDto createdBy;
}
