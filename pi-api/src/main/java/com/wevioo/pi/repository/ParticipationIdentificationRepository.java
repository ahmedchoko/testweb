package com.wevioo.pi.repository;

import com.wevioo.pi.domain.entity.request.ParticipationIdentification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * ParticipationIdentificationRepository repository for  ParticipationIdentification entity
 */
@Repository
public interface ParticipationIdentificationRepository extends CrudRepository<ParticipationIdentification, String> {
}
