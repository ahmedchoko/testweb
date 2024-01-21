package com.wevioo.pi.business.investor;

import com.wevioo.pi.domain.enumeration.PersonTypeEnum;

import java.util.Date;

public interface InvestorListProjection {

    String getId();
    PersonTypeEnum getInvestorType();
    String getFirstName();
    String getLastName();
    String getSocialReason();
    Date getCreationDate();

}
