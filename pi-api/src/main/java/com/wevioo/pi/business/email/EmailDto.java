package com.wevioo.pi.business.email;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO Email for sending MAIL
 *
 * @author shl
 *
 */
@Getter
@Setter
public class EmailDto implements Serializable {

    /**
     * Serial Number
     */
    private static final long serialVersionUID = 5716509521530568293L;

    /**
     * Send to
     */
    private String to;
    /**
     * toGroup
     */
    private List<String> toGroup;
    /**
     * CC
     */
    private String cc;
    /**
     * ccGroup
     */
    private List<String> ccGroup;
    /**
     * subject
     */
    private String subject;
    /**
     * Text
     */
    private String text;
    /**
     * Properties
     */
    private Map<String, Object> properties;
    /**
     * templateName
     */
    private String templateName;

    /**
     * No Args Constructor
     */
    public EmailDto() {
        super();
    }

    /**
     * Constructor for simple mail Message
     *
     * @param to      to
     * @param subject subject
     * @param text    text
     */
    public EmailDto(String to, String subject, String text) {
        super();
        this.to = to;
        this.subject = subject;
        this.text = text;
        this.templateName = null;
    }

    /**
     * Constructor for simple cc Message
     *
     * @param to      to
     * @param cc      cc
     * @param subject subject
     * @param text    text
     */
    public EmailDto(String to, String cc, String subject, String text) {
        super();
        this.to = to;
        this.cc = cc;
        this.subject = subject;
        this.text = text;
    }

    /**
     * Constructor for HTML-template Message
     *
     * @param to           to
     * @param cc           cc
     * @param subject      subject
     * @param properties   properties
     * @param templateName templateName
     */
    public EmailDto(String to, String cc, String subject, Map<String, Object> properties, String templateName) {
        super();
        this.to = to;
        this.cc = cc;
        this.subject = subject;
        this.properties = properties;
        this.templateName = templateName;
    }

    /**
     * Constructor for simple mail Message
     * @param toGroup toGroup
     * @param ccGroup  ccGroup
     * @param subject  subject
     * @param properties properties
     * @param templateName templateName
     */
    public EmailDto(List<String> toGroup, List<String> ccGroup, String subject, Map<String, Object> properties,
                    String templateName) {
        super();
        this.toGroup = toGroup;
        this.ccGroup = ccGroup;
        this.subject = subject;
        this.properties = properties;
        this.templateName = templateName;
    }

}
