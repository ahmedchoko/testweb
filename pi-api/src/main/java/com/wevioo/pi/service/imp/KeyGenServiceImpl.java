package com.wevioo.pi.service.imp;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.domain.entity.referential.KeyGen;
import com.wevioo.pi.domain.enumeration.KeyGenType;
import com.wevioo.pi.exception.DataNotFoundException;
import com.wevioo.pi.repository.KeyGenRepository;
import com.wevioo.pi.service.KeyGenService;

import lombok.extern.slf4j.Slf4j;

/**
 * this class implements {@link KeyGenService} service.
 *
 * @author shl
 *
 */
@Service("keyGenService")
@Slf4j
public class KeyGenServiceImpl implements KeyGenService {

    /**
     * Inject {@link KeyGenRepository} bean.
     */
    @Autowired
    private KeyGenRepository keyGenRespository;

    /**
     * format number.
     *
     * @param number     .
     * @param noOfDigits .
     * @return string.
     */
    private static String formatAutoNumber(final long number, final int noOfDigits) {

        String strNumberIn;
        String strReturn = "";
        int intPadding;
        int i;
        String strPadding;
        int numberLength;

        strNumberIn = String.valueOf(number);
        numberLength = strNumberIn.length();

        if (noOfDigits == 0 || numberLength >= noOfDigits) {
            strReturn = strNumberIn;
        } else {
            intPadding = noOfDigits - numberLength;
            strPadding = "";
            for (i = 1; i <= intPadding; i++) {
                strPadding = strPadding.concat("0");
            }
            strReturn = strPadding.concat(strNumberIn);
        }
        return strReturn;
    }

    /**
     * generate new KeyGen.
     *
     * @param type      keyGen's Type.
     * @param addPrefix .
     * @return KeyGen.
     * @throws NotRespectedRulesException thrown if a required parameter is not
     *                                    valid.
     * @throws NotFoundException          thrown if no keyGen has been found with
     *                                    the corresponding type.
     */
    private synchronized String generateKeyGen(final KeyGenType type, final boolean addPrefix,
                                               String operationLabel) {

        // Get the KeyGen by type.
        final KeyGen keyGen = this.getByType(type.toString(), operationLabel);


        final String nextKey = keyGen.getValue();

        // Check if the KeyGen exists :
        if (ObjectUtils.isEmpty(nextKey)) {
            // : Unable to find the sequence [{0}] into the keygen
            // table.
            log.error(ApplicationConstants.KEY_GEN_NOT_FOUND_ERROR + type.toString());
            throw new DataNotFoundException(ApplicationConstants.KEY_GEN_NOT_FOUND_WITH_TYPE,
                    ApplicationConstants.KEY_GEN_NOT_FOUND_ERROR + type.toString());
        }

        // Increment the number with 1:
        String varTemp = nextKey;

        String year = String.valueOf(LocalDate.now().getYear());
        if (addPrefix) {
            String prefix = keyGen.getPrefix();
            prefix = prefix == null ? "" : prefix;
            varTemp = prefix.concat(year).concat(varTemp);
        }

        log.debug("KeyGen " + varTemp + " generated successfully");
        return varTemp;

    }




    /**
     * {@inheritDoc}.
     */
  
	@Override
    @Transactional
    public KeyGen getByType(final String keyGenType, String criteria) {

        KeyGen keyGen = null;
        if (ObjectUtils.isEmpty(criteria)) {
            keyGen = this.keyGenRespository.findByType(KeyGenType.valueOf(keyGenType));
        } else {
            keyGen = this.keyGenRespository.findByTypeAndCriteria(KeyGenType.valueOf(keyGenType), criteria);
        }
        if (keyGen == null) {
            log.error(ApplicationConstants.KEY_GEN_NOT_FOUND_ERROR + KeyGenType.valueOf(keyGenType));
            throw new DataNotFoundException(ApplicationConstants.KEY_GEN_NOT_FOUND_WITH_TYPE,
                    ApplicationConstants.KEY_GEN_NOT_FOUND_ERROR + KeyGenType.valueOf(keyGenType));
        }

        final String nextKey = KeyGenServiceImpl.formatAutoNumber(Long.valueOf(keyGen.getValue()) + 1,
                keyGen.getDigits());
        keyGen.setValue(nextKey);
        return keyGen;
    }




    /**
     * {@inheritDoc}.
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public String getNextKey(KeyGenType key, boolean addPrefix, String operationLabel) {
        return this.generateKeyGen(key, addPrefix, operationLabel);
    }
}
