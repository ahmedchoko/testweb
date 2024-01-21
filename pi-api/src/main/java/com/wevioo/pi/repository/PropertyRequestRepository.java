package com.wevioo.pi.repository;

import com.wevioo.pi.domain.entity.request.PropertyRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRequestRepository extends CrudRepository<PropertyRequest, String> {

}
