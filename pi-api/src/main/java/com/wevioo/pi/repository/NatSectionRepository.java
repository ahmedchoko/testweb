package com.wevioo.pi.repository;

import com.wevioo.pi.domain.entity.referential.NatSection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * repository of  NatSection Entity
 */
@Repository
public interface NatSectionRepository extends CrudRepository<NatSection,  String> {

    /**
     *
     * @return list of NatSection
     */
    @Query("SELECT s FROM  NatSection  s  ORDER BY  s.label")
    List<NatSection> findAll();
}
