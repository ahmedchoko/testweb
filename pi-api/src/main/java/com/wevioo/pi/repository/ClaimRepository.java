package com.wevioo.pi.repository;

import com.wevioo.pi.domain.entity.account.Claim;
import com.wevioo.pi.domain.enumeration.ClaimStatusEnum;
import com.wevioo.pi.domain.enumeration.UserTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * A repository interface for managing Claim entities using CRUD operations.
 */
@Repository
public interface ClaimRepository extends JpaRepository<Claim, String> {


    /**
     * Find Claims matching the search criteria, assigned to a specific user.
     *
     * @param ref  The ref string to filter Claims by various attributes.
     * @param status  The status string to filter Claims by various attributes.
     * @param creationDate  The creationDate string to filter Claims by various attributes.
     * @param pageable The pagination information for the result.
     * @return A Page of Claims that match the search criteria and are closed.
     */
    @Query("SELECT cl FROM Claim cl   WHERE " +
            "(:status IS NULL OR cl.status = :status ) "
            + "AND (:ref IS NULL OR lower(cl.id) LIKE lower(concat('%', :ref,'%'))) "
            + "AND (:userId IS NULL OR cl.depository.id= :userId)"
            + "AND (:userType IS NULL OR cl.depository.userType= :userType)"
            + "AND (:creationDate IS NULL OR TO_CHAR(cl.creationDate ,'DD-MM-YY') = :creationDate) ")
    Page<Claim> findByCriteria(@Param("userType") UserTypeEnum userType, @Param("ref") String ref , @Param("status") ClaimStatusEnum status , @Param("creationDate") String creationDate, @Param("userId") String userId, Pageable pageable);

    /**
     * Find Claims matching the search criteria , assigned to a ADMIN_BANKER or BANKER  user.
     *
     * @param ref  The ref string to filter Claims by various attributes.
     * @param status  The status string to filter Claims by various attributes.
     * @param creationDate  The creationDate string to filter Claims by various attributes.
     * @param pageable The pagination information for the result.
     * @return A Page of Claims that match the search criteria and are closed.
     */
    @Query("SELECT cl FROM Claim cl   WHERE " +
            "(:status IS NULL OR cl.status = :status ) "
            + "AND (:ref IS NULL OR lower(cl.id) LIKE lower(concat('%', :ref,'%'))) "
            + "AND (:userId IS NULL OR cl.depository.id= :userId)"
            + "AND (cl.depository.userType = 'ADMIN_BANKER' OR cl.depository.userType = 'BANKER')"
            + "AND (:creationDate IS NULL OR TO_CHAR(cl.creationDate ,'DD-MM-YY') = :creationDate) ")
    Page<Claim> findByCriteriaForBankers(@Param("ref") String ref , @Param("status") ClaimStatusEnum status , @Param("creationDate") String creationDate, @Param("userId") String userId, Pageable pageable);


    /**
     * Find Claims matching the search criteria , created with public pages.
     *
     * @param ref  The ref string to filter Claims by various attributes.
     * @param status  The status string to filter Claims by various attributes.
     * @param creationDate  The creationDate string to filter Claims by various attributes.
     * @param pageable The pagination information for the result.
     * @return A Page of Claims that match the search criteria and are closed.
     */
    @Query("SELECT cl FROM Claim cl   WHERE " +
            "(:status IS NULL OR cl.status = :status ) "
            + "AND (:ref IS NULL OR lower(cl.id) LIKE lower(concat('%', :ref,'%'))) "
            + "AND (cl.depository IS NULL) "
            + "AND (:creationDate IS NULL OR TO_CHAR(cl.creationDate ,'DD-MM-YY') = :creationDate) ")
    Page<Claim> findByCriteriaToPublicClaims( @Param("ref") String ref , @Param("status") ClaimStatusEnum status , @Param("creationDate") String creationDate, Pageable pageable);
}
