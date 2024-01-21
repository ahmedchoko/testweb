package com.wevioo.pi.repository;

import com.wevioo.pi.domain.entity.referential.ZipCode;
import com.wevioo.pi.domain.entity.referential.ZipCodeID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ZipCodeRepository extends CrudRepository<ZipCode,ZipCodeID> {

    List<ZipCode> findByIdGovernorateIdAndIdDelegationIdAndIdLocaId(String governorateId, String delegationId, String locaId);




}
