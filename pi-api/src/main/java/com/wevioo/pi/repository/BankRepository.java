package com.wevioo.pi.repository;

import com.wevioo.pi.domain.entity.referential.Bank;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface BankRepository defines all the methods required to manage
 * {@link Bank} objects in database.
 *
 * @author  khn
 */

@Repository
public interface BankRepository  extends CrudRepository<Bank, String> {


    /**
     * Find all Bank order by label
     */
    @Query("SELECT o FROM Bank o ORDER BY LTRIM(RTRIM(o.label)) asc")
    List<Bank> findAll();

    /**
     * Find all Bank  by  list ids
     */

    List<Bank> findAllByIdIn(List<String> ids);
}
