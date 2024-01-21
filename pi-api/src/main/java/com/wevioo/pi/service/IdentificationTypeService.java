package com.wevioo.pi.service;

import com.wevioo.pi.domain.entity.referential.IdentificationType;

import java.util.List;
import java.util.Optional;

public  interface IdentificationTypeService {

    /**
     * @return All Identifier
     */
    List<IdentificationType> findAllIdentifier();

    /**
     * @param identifier
     * @return Pattern by identifier
     */
    String findPatternByIdentifier(String identifier);

    /**
     * @param id
     * @return Identification Type by id
     */
    Optional<IdentificationType> findById(Long id);

    /**
     * @param type
     * @return Identification Type by type
     */
    IdentificationType findByType(String type);

    /**
     * @param type
     * @return Verif Existence by type
     */
    boolean existsByType(String type);
}
