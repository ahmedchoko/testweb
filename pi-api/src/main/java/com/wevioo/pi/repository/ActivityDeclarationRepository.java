package com.wevioo.pi.repository;

import com.wevioo.pi.domain.entity.request.ActivityDeclaration;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 *
 * This interface represents a repository for the ActivityDeclaration entity,
 * providing data access and persistence operations.
 *
 */
@Repository
public interface ActivityDeclarationRepository extends CrudRepository<ActivityDeclaration, String> {

	/**
	 * Delete ActivityDeclaration
	 * 
	 * @param id investIdentification's id
	 */
	@Transactional
	@Modifying
	@Query("DELETE FROM  ActivityDeclaration a WHERE a.investIdentification.id = :id")
	void deleteAllByInvestIdentificationId(String id);
}
