package com.wevioo.pi.repository;


import com.wevioo.pi.domain.entity.referential.Codification;
import com.wevioo.pi.domain.entity.referential.RefCodificationPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CodificationRepository extends JpaRepository<Codification, String> {

    boolean existsById(RefCodificationPK uniqueIdentification);

}
