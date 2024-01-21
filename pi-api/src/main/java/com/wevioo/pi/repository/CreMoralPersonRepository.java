package com.wevioo.pi.repository;

import com.wevioo.pi.domain.entity.referential.CreMoralPerson;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CreMoralPersonRepository extends CrudRepository<CreMoralPerson, Long> {


    /**
     * @param uniqueIdentification
     * @return test the existence of Moral person based on unique identification
     */
    boolean existsByUniqueIdentification (String uniqueIdentification);

    /**
     * @param taxIdentification
     * @return test the existence of Moral person based on tax Identification
     */
    Optional <CreMoralPerson> findCreMoralPersonByUniqueIdentification (String taxIdentification ) ;
    
    
    /**
     * 
     * @return
     */
    @Query("SELECT MAX(c.id) FROM CreMoralPerson c")
	Long findMaximumRank();
}
