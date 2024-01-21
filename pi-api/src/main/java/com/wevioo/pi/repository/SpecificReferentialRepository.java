package com.wevioo.pi.repository;

import com.wevioo.pi.domain.entity.request.referential.CategorySpecificReferential;
import com.wevioo.pi.domain.entity.request.referential.SpecificReferential;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * repository for  SpecificReferential Entity
 */
@Repository
public interface SpecificReferentialRepository extends CrudRepository<SpecificReferential, Long> {
    /**
     * findAll By  id Category
     * @param id category
     * @return list  SpecificReferential
     */
    List<SpecificReferential> findAllByCategorySpecificReferentialId(Long  id);

    /**
     * find AllBy  Category Specific Referential Id In
     * @param ids
     * @return list  CategorySpecificReferential
     */

    @Query("SELECT c from  CategorySpecificReferential  c WHERE  c.id  IN :ids")
    List<CategorySpecificReferential> findAllByCategorySpecificReferentialIdIn(@Param("ids") List<Long>  ids);

    /**
     *  findAllByParentId
     * @param parentId id parent
     * @return list  of  SpecificReferential
     */
    List<SpecificReferential> findAllByParentId(Long parentId);

    /**
     * Find specificReferential by their IDs.
     *
     * @param ids A list of specificReferential IDs to search for.
     * @return A list of specificReferentials matching the provided IDs.
     */

    @Query("SELECT s FROM SpecificReferential  s WHERE  s.id  In :ids " )
    List<SpecificReferential> findAllByIds(@Param("ids") List<Long> ids);


}
