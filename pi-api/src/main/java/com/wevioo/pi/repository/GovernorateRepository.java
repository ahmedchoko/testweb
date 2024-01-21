package com.wevioo.pi.repository;

import com.wevioo.pi.domain.entity.referential.Governorate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GovernorateRepository extends CrudRepository<Governorate,String> {

    @Query("SELECT o FROM Governorate o ORDER BY LTRIM(RTRIM(o.label)) asc")
    List<Governorate> findAll();
}
