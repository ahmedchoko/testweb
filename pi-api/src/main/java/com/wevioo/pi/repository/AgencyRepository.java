package com.wevioo.pi.repository;


import com.wevioo.pi.domain.entity.referential.Agency;
import com.wevioo.pi.domain.entity.referential.AgencyId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository provides methods to access {@link  com.wevioo.pi.domain.entity.referential.Agency}
 */
@Repository
public interface AgencyRepository extends CrudRepository<Agency , AgencyId> {
    /**
     * Find all agencies by given parameter: idBank
     *
     * @param idBank bank id bank's id
     * @return list agencies
     */
    List<Agency> findByBankIdOrderByLabelAsc(String idBank);


}
