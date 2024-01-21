package com.wevioo.pi.repository;

import com.wevioo.pi.domain.entity.request.MainContractualTerms;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MainContractualTermsRepository extends CrudRepository<MainContractualTerms, String> {


}
