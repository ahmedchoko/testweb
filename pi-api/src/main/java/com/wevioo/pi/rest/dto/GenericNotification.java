package com.wevioo.pi.rest.dto;


import com.wevioo.pi.domain.enumeration.Language;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

@Builder
@Getter
@Setter
public class GenericNotification implements Serializable {


    private static final long serialVersionUID = 7846513936766381673L;

    /**
     * attributes of mail content
     */
    private Map<String, String> attributes;
    /**
     * label
     */
    private String label ;
    /**
     * language
     */
    private Language language ;
    /**
     * emailFrom
     */
    private String emailFrom;
    /**
     * emailTo
     */
    private String emailTo;
}
