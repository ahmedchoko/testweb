package com.wevioo.pi.repository;

import com.wevioo.pi.domain.entity.referential.NatClass;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *  repository of NatClass entity
 */
@Repository
public interface NatClassRepository extends CrudRepository<NatClass , String> {
    /**
     * find All By NatSection Id And Nat SubSection Id And Group Id
     * @param sectionId
     * @param subSectionId
     * @param groupId
     * @return  list of NatClass
     */
    List<NatClass> findAllByNatSectionIdAndNatSubSectionIdAndGroupId(
            String sectionId,
            String subSectionId,
            String groupId
    );
}
