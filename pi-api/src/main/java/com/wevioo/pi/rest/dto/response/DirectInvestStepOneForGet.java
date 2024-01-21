package com.wevioo.pi.rest.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class DirectInvestStepOneForGet implements Serializable {

                 /**
                 * The serial version ID
                 */
                private static final long serialVersionUID = -9193199692996049806L;

                private String ficheInvestId;

                private Boolean hasRequester;

                private RequesterForGetDto requester;

                private  String investorId;
}
