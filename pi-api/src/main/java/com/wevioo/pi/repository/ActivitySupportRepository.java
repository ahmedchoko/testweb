package com.wevioo.pi.repository;

import com.wevioo.pi.domain.entity.request.ActivitySupport;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 *
 * This interface represents a repository for the ActivitySupport entity,
 * providing data access and persistence operations.
 *
 */
@Repository
public interface ActivitySupportRepository extends CrudRepository<ActivitySupport, String> {

	/**
	 * Delete ActivitySupport
	 * 
	 * @param id investIdentification's id
	 */
	@Transactional
	@Modifying
	@Query("DELETE FROM   ActivitySupport a WHERE a.investIdentification.id = :id")
	void deleteAllByInvestIdentificationId(String id);
}
