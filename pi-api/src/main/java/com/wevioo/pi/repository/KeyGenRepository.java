package com.wevioo.pi.repository;

import com.wevioo.pi.domain.entity.referential.KeyGen;
import com.wevioo.pi.domain.enumeration.KeyGenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;

/**
 * KeyGen repository.
 *
 * @author kad
 *
 */
public interface KeyGenRepository extends JpaRepository<KeyGen, Long> {

    /**
     * returns {@link KeyGen} given by.
     *
     * @param type
     *            .
     * @return {@link KeyGen}.
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    KeyGen findByType(KeyGenType type);

    /**
     * returns {@link KeyGen} given by.
     *
     * @param type KeyGen's type
     * 	 * @param operationLabel KeyGen's operationLabel
     *            .
     * @return {@link KeyGen}.
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    KeyGen findByTypeAndCriteria(KeyGenType type, String criteria);

}
