package com.wevioo.pi.repository;


import com.wevioo.pi.domain.entity.referential.IdentificationType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;


@Repository
public interface IdentificationTypeRepository extends CrudRepository<IdentificationType, Long> {

    List<IdentificationType> findAllByIsActiveTrue();

    @Query("Select p.pattern from IdentificationType p where p.type = :type")
    String getPatternByType(@PathParam("type") String type);

    Optional<IdentificationType> findByType(String type);

    boolean existsByType(String type);
}
