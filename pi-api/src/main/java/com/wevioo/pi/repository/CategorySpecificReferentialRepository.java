package com.wevioo.pi.repository;

import com.wevioo.pi.domain.entity.request.referential.CategorySpecificReferential;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorySpecificReferentialRepository extends CrudRepository<CategorySpecificReferential, Long> {
}
