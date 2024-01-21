package com.wevioo.pi.repository;

import com.wevioo.pi.domain.entity.referential.Location;
import com.wevioo.pi.domain.entity.referential.LocationID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Location Repository  Entity  Location
 */
@Repository
public interface LocationRepository extends CrudRepository<Location, LocationID> {

    List<Location> findByIdGovernorateIdAndIdDelegationId(String governorateId, String delegationId);



}
