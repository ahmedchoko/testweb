package com.wevioo.pi.repository;

import com.wevioo.pi.domain.entity.account.BctAgent;
import com.wevioo.pi.domain.enumeration.UserTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * interface repository for  BctAgent entity
 */

@Repository
public interface IBctAgentRepository extends JpaRepository<BctAgent , String> {


    @Query("SELECT   b  FROM  BctAgent  b  WHERE " +
            "(:id IS  NUll  OR  b.id =:id)" +
            "AND (:registrationNumber IS NULL  OR  b.registrationNumber =:registrationNumber)  " +
            "AND b.userType =:userTypeEnum ")
   Page<BctAgent> findAllBySearch(@Param("id") String id ,
                                  @Param("registrationNumber") String registrationNumber,
                                  @Param("userTypeEnum") UserTypeEnum userTypeEnum , Pageable pageable);

  Optional<BctAgent>  findFirstByRegistrationNumberIgnoreCase(String registrationNumber);
  Optional<BctAgent>  findFirstByRegistrationNumberIgnoreCaseAndIdNot(String registrationNumber , String id);
}
