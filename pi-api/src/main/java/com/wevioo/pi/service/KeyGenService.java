package com.wevioo.pi.service;

import com.wevioo.pi.domain.entity.referential.KeyGen;

import com.wevioo.pi.domain.enumeration.KeyGenType;

/**
 * this interface defines method to manage {@link KeyGen} models.
 *
 * @author kad
 *
 */
public interface KeyGenService {

    /**
     * returns keyGen given by.
     *
     * @param keyGenType
     * @return {@link KeyGen}.
     *
     */
    KeyGen getByType(String keyGenType ,  String operationLabel);
    /**
     * Generates and returns the following key according to the latest generated
     * key.
     * <p>
     * The generated key is unique.
     *
     * @param addPrefix      Boolean to say if we add prefix or not.
     *
     * @return the generated entity key.
     *
     */
    String getNextKey(KeyGenType key, boolean addPrefix , String  operationLabel);

}
