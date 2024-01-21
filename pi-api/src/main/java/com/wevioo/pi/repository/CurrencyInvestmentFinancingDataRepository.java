package com.wevioo.pi.repository;

import com.wevioo.pi.domain.entity.request.CurrencyFinancingData;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyInvestmentFinancingDataRepository extends CrudRepository<CurrencyFinancingData, String> {

    @Query("SELECT cfd from CurrencyFinancingData cfd where cfd.currencyFinancing.id = :currencyFinancingId")
    List<CurrencyFinancingData> findByCurrencyFinancingId(String currencyFinancingId);

    @Modifying
    @Query("DELETE from CurrencyFinancingData cfd where cfd.currencyFinancing.id = :currencyFinancing")
    void deleteAllByCurrencyFinancingId(String currencyFinancing);
}
