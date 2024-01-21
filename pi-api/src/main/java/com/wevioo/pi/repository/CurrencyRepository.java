package com.wevioo.pi.repository;

import com.wevioo.pi.domain.entity.referential.Currency;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * A repository interface for managing Currency entities using CRUD operations.
 */
public interface CurrencyRepository extends CrudRepository<Currency, String> {

    /**
     * Find currencies and order them by label in ascending order after trimming leading and trailing spaces.
     *
     * @return A list of currencies ordered by label in ascending order with leading and trailing spaces trimmed.
     */
    @Query("SELECT o FROM Currency o ORDER BY LTRIM(RTRIM(o.label)) ASC")
    List<Currency> findByOrderByLabelAsc();

    /**
     * Find currencies by list of ids
     * @param ids  list of ids
     * @return
     */
    List<Currency> findAllByIdIn(List<String> ids) ;
}
