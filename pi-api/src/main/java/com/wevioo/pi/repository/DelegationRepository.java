package com.wevioo.pi.repository;

import com.wevioo.pi.domain.entity.referential.Delegation;
import com.wevioo.pi.domain.entity.referential.DelegationID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository of entity  Delegation
 */
@Repository
public interface DelegationRepository extends CrudRepository<Delegation,  DelegationID> {

    List<Delegation> findByIdGovernorateId(String governorateId);

}
