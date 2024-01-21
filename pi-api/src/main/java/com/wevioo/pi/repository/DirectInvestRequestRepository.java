package com.wevioo.pi.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wevioo.pi.domain.entity.request.DirectInvestRequest;
import com.wevioo.pi.domain.enumeration.OperationType;
import com.wevioo.pi.domain.enumeration.RequestStatusEnum;
import com.wevioo.pi.rest.dto.DirectInvestDto;

@Repository
public interface DirectInvestRequestRepository extends JpaRepository<DirectInvestRequest, String> {

	@Query("SELECT dInv  FROM  DirectInvestRequest as  dInv left join dInv.bank  left join dInv.investIdentification left join dInv.affectedTo WHERE "
			+ "(:reference IS NULL  OR :reference='' OR lower(dInv.id) LIKE lower(concat('%', :reference, '%')) )"
			+ " AND (:invistor IS NULL OR ((lower(dInv.invistor.nameForFund) LIKE lower(concat('%', :invistor,'%')) ) OR (lower(dInv.invistor.socialReason) LIKE lower(concat('%', :invistor,'%')) ) OR (lower(CONCAT(dInv.invistor.firstName,' ',dInv.invistor.lastName)) LIKE lower(concat('%', :invistor,'%')) ) )) "

			+ " AND (COALESCE( :types) IS NULL   OR  dInv.operationType IN :types )"
			+ " AND (:companyName IS NULL  OR :companyName='' OR   lower(dInv.investIdentification.companyName) like lower(concat('%', :companyName, '%'))) "
			+ " AND (:investorIdentifier IS NULL  OR :investorIdentifier='' OR   lower(dInv.invistor.id) like lower(concat('%', :investorIdentifier, '%'))) "
			+ " AND (:companyIdentifier IS NULL  OR :companyIdentifier='' OR   lower(dInv.investIdentification.uniqueIdentifier) like lower(concat('%', :companyIdentifier, '%'))) "
			+ " AND (COALESCE(:banks) IS NULL OR (dInv.bank.code IN :banks)) "
			+ " AND (COALESCE(:ids) IS NULL OR (dInv.affectedTo.id IN :ids)) "
			+ " AND (:userId IS NULL OR (dInv.createdBy.id = :userId)) "
			+ " AND  (COALESCE(:status) IS NULL  OR dInv.status IN :status)"
			+ " AND  (:statusReq IS NULL  OR dInv.status <> :statusReq)")

	Page<DirectInvestRequest> findDirectInvestRequestByCriteria(@Param("reference") String reference,
			@Param("invistor") String investor, @Param("types") List<OperationType> type,
			@Param("companyName") String companyName, @Param("companyIdentifier") String companyIdentifier,
			@Param("banks") List<String> banks, @Param("status") List<RequestStatusEnum> status,
			@Param("investorIdentifier") String investorIdentifier, @Param("userId") String userId,
			@Param("ids") List<String> userIds, @Param("statusReq") RequestStatusEnum statusReq, Pageable pageable);


	@Query("SELECT dInv  FROM  DirectInvestRequest as  dInv left join dInv.bank  left join dInv.investIdentification left join dInv.affectedTo WHERE "
			+ "(:reference IS NULL  OR :reference='' OR lower(dInv.id) LIKE lower(concat('%', :reference, '%')) )"
			+ " AND (:invistor IS NULL OR ((lower(dInv.invistor.nameForFund) LIKE lower(concat('%', :invistor,'%')) ) OR (lower(dInv.invistor.socialReason) LIKE lower(concat('%', :invistor,'%')) ) OR (lower(CONCAT(dInv.invistor.firstName,' ',dInv.invistor.lastName)) LIKE lower(concat('%', :invistor,'%')) ) )) "

			+ " AND (COALESCE( :types) IS NULL   OR  dInv.operationType IN :types )"
			+ " AND (:companyName IS NULL  OR :companyName='' OR   lower(dInv.investIdentification.companyName) like lower(concat('%', :companyName, '%'))) "
			+ " AND (:investorIdentifier IS NULL  OR :investorIdentifier='' OR   lower(dInv.invistor.id) like lower(concat('%', :investorIdentifier, '%'))) "
			+ " AND (:companyIdentifier IS NULL  OR :companyIdentifier='' OR   lower(dInv.investIdentification.uniqueIdentifier) like lower(concat('%', :companyIdentifier, '%'))) "
			+ " AND (COALESCE(:banks) IS NULL OR (dInv.bank.code IN :banks)) "
			+ " AND (COALESCE(:ids) IS NULL OR (dInv.affectedTo.id IN :ids)) "
			+ " AND (:userId IS NULL OR (dInv.createdBy.id = :userId)) "
			+ " AND  (COALESCE(:status) IS NULL  OR dInv.status IN :status)"
			+ " AND  (:statusReq IS NULL  OR dInv.status <> :statusReq)")

	List<DirectInvestRequest> findDirectInvestRequestByCriteriaExport(@Param("reference") String reference,
																@Param("invistor") String investor, @Param("types") List<OperationType> type,
																@Param("companyName") String companyName, @Param("companyIdentifier") String companyIdentifier,
																@Param("banks") List<String> banks, @Param("status") List<RequestStatusEnum> status,
																@Param("investorIdentifier") String investorIdentifier, @Param("userId") String userId,
																@Param("ids") List<String> userIds, @Param("statusReq") RequestStatusEnum statusReq);
	
	
	
	@Query("SELECT dInv FROM  DirectInvestRequest as  dInv left join dInv.bank  left join dInv.investIdentification  WHERE "
			+ "(:reference IS NULL  OR :reference='' OR lower(dInv.id) LIKE lower(concat('%', :reference, '%')) )"
			+ " AND (COALESCE( :types) IS NULL   OR  dInv.operationType IN :types )"
			+ " AND (:companyName IS NULL  OR :companyName='' OR   lower(dInv.investIdentification.companyName) like lower(concat('%', :companyName, '%'))) "
			+ " AND (:companyIdentifier IS NULL  OR :companyIdentifier='' OR   lower(dInv.investIdentification.uniqueIdentifier) like lower(concat('%', :companyIdentifier, '%'))) "
			+ " AND (COALESCE(:banks) IS NULL OR (dInv.bank.code IN :banks)) "	
			+ " AND  (COALESCE(:status) IS NULL  OR dInv.status IN :status)"
			+ " AND (dInv.createdBy.id = :userId or dInv.invistor.id = :investorIdentifier) ")

	Page<DirectInvestRequest> findDirectInvestRequestByCriteria(@Param("reference") String reference,
			@Param("types") List<OperationType> type, @Param("companyName") String companyName,
			@Param("companyIdentifier") String companyIdentifier, @Param("banks") List<String> banks,
			@Param("status") List<RequestStatusEnum> status, @Param("userId") String userId,
		@Param("investorIdentifier") String investorIdentifier, Pageable pageable);

	@Query("SELECT dInv FROM  DirectInvestRequest as  dInv left join dInv.bank  left join dInv.investIdentification  WHERE "
			+ "(:reference IS NULL  OR :reference='' OR lower(dInv.id) LIKE lower(concat('%', :reference, '%')) )"
			+ " AND (COALESCE( :types) IS NULL   OR  dInv.operationType IN :types )"
			+ " AND (:companyName IS NULL  OR :companyName='' OR   lower(dInv.investIdentification.companyName) like lower(concat('%', :companyName, '%'))) "
			+ " AND (:companyIdentifier IS NULL  OR :companyIdentifier='' OR   lower(dInv.investIdentification.uniqueIdentifier) like lower(concat('%', :companyIdentifier, '%'))) "
			+ " AND (COALESCE(:banks) IS NULL OR (dInv.bank.code IN :banks)) "
			+ " AND  (COALESCE(:status) IS NULL  OR dInv.status IN :status)"
			+ " AND (dInv.createdBy.id = :userId or dInv.invistor.id = :investorIdentifier) ")

	List<DirectInvestRequest> findDirectInvestRequestByCriteriaExport(@Param("reference") String reference,
																@Param("types") List<OperationType> type, @Param("companyName") String companyName,
																@Param("companyIdentifier") String companyIdentifier, @Param("banks") List<String> banks,
																@Param("status") List<RequestStatusEnum> status, @Param("userId") String userId,
																@Param("investorIdentifier") String investorIdentifier);
}
