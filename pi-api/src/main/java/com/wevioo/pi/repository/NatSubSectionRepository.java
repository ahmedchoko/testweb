package com.wevioo.pi.repository;

import com.wevioo.pi.domain.entity.referential.NatSubSection;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository of  NatSubSection Entity
 */

@Repository
public interface NatSubSectionRepository extends CrudRepository<NatSubSection , String> {

    /**
     * find All By Nat Section Id
     * @param natSectionId
     * @return list of NatSubSection
     */
    List<NatSubSection> findAllByNatSectionId(String natSectionId);
}
