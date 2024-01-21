package com.wevioo.pi.repository;


import com.wevioo.pi.domain.entity.referential.CrePhysicPerson;
import com.wevioo.pi.rest.dto.response.CrePersonForGetProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CrePhysicPersonRepository extends CrudRepository<CrePhysicPerson, Long> {

    /**
     * @param cin
     * @return get the physic person dto ( lastName , firstName) if exists based on cin
     */
    @Query("SELECT ph.lastname as lnom , ph.firstname as lprenom FROM CrePhysicPerson ph WHERE ph.typdoc = :typdoc AND ph.ndocid = :cin ")
    Optional<CrePersonForGetProjection> findPhysicPersonByCin (@Param("cin") String cin , @Param("typdoc") String typdoc);
}
