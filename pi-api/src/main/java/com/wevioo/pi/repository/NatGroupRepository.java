package com.wevioo.pi.repository;

import com.wevioo.pi.domain.entity.referential.NatGroup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository of NatGroup Entity
 */

@Repository
public interface NatGroupRepository  extends CrudRepository<NatGroup , String> {
    /**
     * find All By Nat Section Id And Nat Sub Section Id
     * @param sectionId  section Id
     * @param subSectionId subSection Id
     * @return list of NatGroup
     */
    List<NatGroup> findAllByNatSectionIdAndNatSubSectionId(String sectionId , String subSectionId);
}
