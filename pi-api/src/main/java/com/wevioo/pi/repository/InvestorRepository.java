package com.wevioo.pi.repository;

import com.wevioo.pi.business.investor.InvestorListProjection;
import com.wevioo.pi.domain.entity.account.Investor;
import com.wevioo.pi.domain.enumeration.PersonTypeEnum;
import com.wevioo.pi.domain.enumeration.IdentificationTypeEnum;
import com.wevioo.pi.rest.dto.response.InvestorCheckForGetProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * This interface represents a repository for the Investor entity, providing data access and persistence operations.
 * @author  shl
 */
@Repository
public interface InvestorRepository extends JpaRepository<Investor, String> {

    @Query("SELECT i.id AS id, i.investorType AS investorType, i.firstName AS firstName, " +
            "i.lastName AS lastName, i.socialReason AS socialReason, i.creationDate AS creationDate FROM Investor i")
    List<InvestorListProjection> findAllInvestorsList();


    /**
     * Search based on criteria , in case IdetificationType ( CIN , PASSPORT , UNIQUEIDENTIFIER ) because there is no
     * identificationType attribute , we test based on  nationalId , passportNumber ,uniqueId values not null
     * @param investorType
     * @param systemId
     * @param email
     * @param investor
     * @param bankerId
     * @param identificationValue
     * @param identificationTypes
     * @param pageable
     * @return
     */
    @Query("SELECT investor FROM Investor investor " +
            "WHERE " +
            "(:systemId IS NULL OR lower(investor.id) LIKE lower(concat('%', :systemId, '%'))) " +
            "AND (COALESCE(:investorType) IS NULL OR investor.investorType IN :investorType) " +
            // identificationType search based on nationalId , passportNumber , uniqueId values not null
            "AND (COALESCE(:identificationTypes) IS NULL OR " +
            "   ('CIN' IN :identificationTypes AND investor.nationalId IS NOT NULL) OR " +
            "   ('PASSPORT' IN :identificationTypes AND investor.passportNumber IS NOT NULL) OR " +
            "   ('UNIQUE_IDENTIFIER' IN :identificationTypes AND investor.uniqueId IS NOT NULL)) " +
            "AND (:identificationValue IS NULL OR " +
            "   (lower(investor.nationalId) LIKE lower(concat('%', :identificationValue, '%'))) OR " +
            "   (lower(investor.uniqueId) LIKE lower(concat('%', :identificationValue, '%'))) OR " +
            "   (lower(investor.passportNumber) LIKE lower(concat('%', :identificationValue, '%')))) " +
            "AND (:investor IS NULL OR " +
            "   (lower(investor.nameForFund) LIKE lower(concat('%', :investor, '%'))) OR " +
            "   (lower(investor.socialReason) LIKE lower(concat('%', :investor, '%'))) OR " +
            "   (lower(CONCAT(investor.firstName, ' ', investor.lastName)) LIKE lower(concat('%', :investor, '%')))) " +
            "AND (:email IS NULL OR lower(investor.login) LIKE lower(concat('%', :email, '%'))) " +
            "AND (:bankerId IS NULL OR investor.createdBy.id = :bankerId) " +
            "ORDER BY investor.creationDate DESC")
    Page<Investor> findAllBySearch(
            @Param("investorType") List<PersonTypeEnum> investorType,
            @Param("systemId") String systemId,
            @Param("email") String email,
            @Param("investor") String investor,
            @Param("bankerId") String bankerId,
            @Param("identificationValue") String identificationValue,
            @Param("identificationTypes") List<String> identificationTypes,
            Pageable pageable
    );



    /**
     * Search based on criteria , in case IdetificationType ( CIN , PASSPORT , UNIQUEIDENTIFIER ) because there is no
     * identificationType attribute , we test based on  nationalId , passportNumber ,uniqueId values not null
     * @param investorType
     * @param systemId
     * @param email
     * @param investor
     * @param bankerId
     * @param identificationValue
     * @param identificationTypes
     * @return
     */
    @Query("SELECT investor FROM Investor investor " +
            "WHERE " +
            "(:systemId IS NULL OR lower(investor.id) LIKE lower(concat('%', :systemId, '%'))) " +
            "AND (COALESCE(:investorType) IS NULL OR investor.investorType IN :investorType) " +
            // identificationType search based on nationalId , passportNumber , uniqueId values not null
            "AND (COALESCE(:identificationTypes) IS NULL OR " +
            "   ('CIN' IN :identificationTypes AND investor.nationalId IS NOT NULL) OR " +
            "   ('PASSPORT' IN :identificationTypes AND investor.passportNumber IS NOT NULL) OR " +
            "   ('UNIQUE_IDENTIFIER' IN :identificationTypes AND investor.uniqueId IS NOT NULL)) " +
            "AND (:identificationValue IS NULL OR " +
            "   (lower(investor.nationalId) LIKE lower(concat('%', :identificationValue, '%'))) OR " +
            "   (lower(investor.uniqueId) LIKE lower(concat('%', :identificationValue, '%'))) OR " +
            "   (lower(investor.passportNumber) LIKE lower(concat('%', :identificationValue, '%')))) " +
            "AND (:investor IS NULL OR " +
            "   (lower(investor.nameForFund) LIKE lower(concat('%', :investor, '%'))) OR " +
            "   (lower(investor.socialReason) LIKE lower(concat('%', :investor, '%'))) OR " +
            "   (lower(CONCAT(investor.firstName, ' ', investor.lastName)) LIKE lower(concat('%', :investor, '%')))) " +
            "AND (:email IS NULL OR lower(investor.login) LIKE lower(concat('%', :email, '%'))) " +
            "AND (:bankerId IS NULL OR investor.createdBy.id = :bankerId) " +
            "ORDER BY investor.creationDate DESC")
    List<Investor> findAllBySearchExport(
            @Param("investorType") List<PersonTypeEnum> investorType,
            @Param("systemId") String systemId,
            @Param("email") String email,
            @Param("investor") String investor,
            @Param("bankerId") String bankerId,
            @Param("identificationValue") String identificationValue,
            @Param("identificationTypes") List<String> identificationTypes
    );


    @Query("SELECT  investor FROM  Investor  investor " +
            "WHERE " +
            "(investor.createdBy.id =:bankerId)")
    Page<Investor> findAllInvestorsForBankerBySearch(
            @Param("bankerId") String bankerId,
            Pageable pageable
    );


    @Query("SELECT inve.firstName as firstName , inve.lastName as lastName , inve.uniqueId as  uniqueId , inve.socialReason as socialReason , inve.nameForFund as nameForFund  FROM Investor inve WHERE inve.id = :id")
    Optional<InvestorCheckForGetProjection> checkInvestor (@Param("id") String id);


    @Query("SELECT CASE WHEN COUNT(i) > 0 THEN true ELSE false END FROM Investor i " +
            "WHERE (:uniqueId IS NOT NULL AND i.uniqueId = :uniqueId) " +
            "   OR (:nationalId IS NOT NULL AND i.nationalId = :nationalId) " +
            "   OR (:passportNumber IS NOT NULL AND i.passportNumber = :passportNumber)")
    boolean existsByUniqueOrNationalIdOrPassportNumber(
            String uniqueId, String nationalId, String passportNumber);

    boolean existsByPassportNumberAndIdIsNotLike ( String passportNumber , String idInvestor);
}
