package com.wevioo.pi.repository;

import com.wevioo.pi.domain.entity.request.Requester;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Requester Repository for Requester entity;
 */
@Repository
public interface RequesterRepository extends CrudRepository<Requester, String> {
}
