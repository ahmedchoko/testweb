package com.wevioo.pi.repository;

import com.wevioo.pi.domain.entity.request.CurrencyFinancing;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyInvestmentFinancingRepository extends CrudRepository<CurrencyFinancing, String> {
}
