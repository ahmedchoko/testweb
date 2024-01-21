package com.wevioo.pi.repository;


import com.wevioo.pi.domain.entity.config.EmailTemplate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


/**
 * EmailRepository
 */
@Repository
public interface EmailRepository extends CrudRepository<EmailTemplate,Long> {

    /**
     * @param label
     * @return Email Template based on it's label
     */
    EmailTemplate findEmailTemplateByLabel (String label);
}
