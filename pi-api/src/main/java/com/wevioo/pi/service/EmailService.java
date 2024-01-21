package com.wevioo.pi.service;

import com.wevioo.pi.business.email.EmailDto;
import com.wevioo.pi.rest.dto.GenericNotification;

import javax.mail.MessagingException;
import java.util.Map;

public interface EmailService {

    /**
     * Send simple email
     *
     * @param dto {@link EmailDto}
     */
    void sendSimpleMessage(EmailDto dto);

    /**
     * Send HTML email
     *
     * @param dto {@link EmailDto}
     */
    void sendHtmlMessage(EmailDto dto);

    /**
     * send Mail with Generic Notification Template
     * @param template
     * @throws MessagingException
     */
    void sendEmailSpecificTemplate(GenericNotification template ) throws MessagingException;


}
