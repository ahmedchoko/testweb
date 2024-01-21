package com.wevioo.pi.repository;

import com.wevioo.pi.domain.entity.account.Banker;
import com.wevioo.pi.domain.enumeration.UserTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * This interface represents a repository for the Banker entity, providing data
 * access and persistence operations.
 *
 * @param <Banker> The type of entity being managed, in this case, the Banker
 *                 entity.
 * @param <        String> The type of the primary key for the entity.
 *
 * @author knh
 */
@Repository
public interface IBankerRepository extends JpaRepository<Banker, String> {

	List<Banker> findAllByUserTypeAndAndApprovedIntermediaryId(UserTypeEnum userTypeEnum, String bankId);

	List<Banker> findAllByParentId(String parentId);

	List<Banker> findAllByApprovedIntermediaryIdAndParentIsNotNull(String bankId);

	/**
	 * Search all active Banker ID by given BankID
	 * 
	 * @param bankId bank's id
	 * @return list of banker id
	 */

	@Query("SELECT  banker.id FROM  Banker  banker WHERE banker.isActive =true AND banker.parent IS NOT NULL "
			+ " AND banker.approvedIntermediary.id =:bankId ")
	List<String> findAllBankerByApprovedIntermediaryId(@Param("bankId") String bankId);

	@Query("SELECT  banker FROM  Banker  banker " + "WHERE " + "(:bankerId IS NULL  OR   banker.id =:bankerId) "
			+ "AND (:bankId IS NULL  OR  banker.approvedIntermediary.id =:bankId) " + "AND (banker.isAdmin =:isAdmin)")
	Page<Banker> findAllBySearch(@Param("bankerId") String bankerId, @Param("bankId") String bankId,
			@Param("isAdmin") Boolean isAdmin, Pageable pageable);

	@Query("SELECT  banker FROM  Banker  banker " + "WHERE " + "(:bankerId IS NULL  OR   banker.id =:bankerId) "
			+ "AND (:bankId IS NULL  OR  banker.approvedIntermediary.id =:bankId) " + "AND (banker.isAdmin =:isAdmin)")
	List<Banker> findAllBySearchExport(@Param("bankerId") String bankerId, @Param("bankId") String bankId,
								 @Param("isAdmin") Boolean isAdmin);

	@Query("SELECT  banker FROM  Banker  banker " + "WHERE " + "(:bankerId IS NULL  OR   banker.id =:bankerId) "
			+ " AND (banker.parent.id =:parentId) " + "AND (banker.isAdmin =:isAdmin)")
	Page<Banker> findAllBanker(@Param("bankerId") String bankerId, @Param("parentId") String parentId,
			@Param("isAdmin") Boolean isAdmin, Pageable pageable);

	@Query("SELECT  banker FROM  Banker  banker " + "WHERE " + "(:bankerId IS NULL  OR   banker.id =:bankerId) "
			+ " AND (banker.parent.id =:parentId) " + "AND (banker.isAdmin =:isAdmin)")
	List<Banker> findAllBankerExport(@Param("bankerId") String bankerId, @Param("parentId") String parentId,
							   @Param("isAdmin") Boolean isAdmin);
	/**
	 * find banker by bank id and is active
	 * 
	 * @param bankId
	 * @param isActive
	 * @return
	 */
	Optional<Banker> findFirstByApprovedIntermediaryIdAndIsActive(String bankId, Boolean isActive);

	/**
	 * find banker by bank id and is active
	 * 
	 * @param bankId
	 * @param isActive
	 * @return
	 */
	Optional<Banker> findFirstByApprovedIntermediaryIdAndIsAdmin(String bankId, Boolean isActive);

	/**
	 * find banker by bank id and is active
	 * 
	 * @param bankId
	 * @param isActive
	 * @return
	 */
	Optional<Banker> findFirstByApprovedIntermediaryIdAndIsActiveAndIdNot(String bankId, Boolean isActive, String id);

	/**
	 * find list of bankers by bank id and is active and not admin
	 * 
	 * @param bankId
	 * @param isActive
	 * @return list of bankers active not admin of certain bank
	 */
	@Query("SELECT  banker FROM  Banker  banker WHERE banker.isActive =true AND banker.parent IS NOT NULL "
			+ " AND banker.approvedIntermediary.id =:bankId  AND banker.investmentDirectAndRealEstate= :isInvestmentDirectAndRealEstate")
	List<Banker> findByApprovedIntermediaryIdAndIsActiveAndIsAdminAndInvestmentDirectAndRealEstate(
			@Param("bankId") String bankId, 
			@Param("isInvestmentDirectAndRealEstate") Boolean investmentDirectAndRealEstate);

	
	/**
	 * find list of bankers by bank id and is active and not admin
	 * 
	 * @param bankId
	 * @param isActive
	 * @return list of bankers active not admin of certain bank
	 */
	@Query("SELECT  banker FROM  Banker  banker WHERE banker.isActive =true AND banker.parent IS NOT NULL "
			+ " AND banker.approvedIntermediary.id =:bankId  AND banker.investmentPortfolio= :isInvestmentPortfolio")
	List<Banker> findByApprovedIntermediaryIdAndIsActiveAndIsAdminAndInvestmentPortfolio(@Param("bankId") String bankId, 
			@Param("isInvestmentPortfolio") Boolean investmentPortfolio );
}
