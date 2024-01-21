package com.wevioo.pi.repository;

import com.wevioo.pi.domain.entity.account.ActivationLink;
import com.wevioo.pi.domain.entity.account.User;
import com.wevioo.pi.domain.enumeration.LinkEnum;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface ActivationLinkRepository extends CrudRepository<ActivationLink, String> {

    /**
     * Find activation link by activationKey
     *
     * @param activationKey ActivationLink's activationKey
     * @return ActivationLink
     */
    ActivationLink findFirstByActivationKey(String activationKey);

    /**
     * Find activation link by user and link type
     *
     * @param user User
     * @param type LinkEnum
     * @return ActivationLink
     */
    ActivationLink findFirstByUserAndType(User user, LinkEnum type);

    /**
     * Find ActivationLink by given parameter : user, type, activationKey
     *
     * @param user          User
     * @param type          LinkEnum
     * @param activationKey activationKey
     * @return ActivationLink
     */
    ActivationLink findFirstByUserAndTypeAndActivationKey(User user, LinkEnum type, String activationKey);

    /**
     * Find expired Activation Links
     *
     * @param now Date
     * @return list of ActivationLink
     */
    List<ActivationLink> findByExpirationDateLessThanEqual(Date now);

    /**
     * Delete expired FORGOT_PASSWORD and OTP_ACTIVATION Activation Links
     */
    @Transactional
    @Modifying
    @Query("DELETE FROM ActivationLink a WHERE (a.numberAttempts >= 3 OR a.expirationDate < CURRENT_DATE) AND (a.type ='OTP_ACTIVATION' OR a.type ='PASSWORD_FORGOT')")
    void deleteForgotPasswordAndOtpActivationLinks();

    /**
     * Delete expired ACCOUNT_CREATION Activation Links
     */
    @Transactional
    @Modifying
    @Query("DELETE FROM ActivationLink a WHERE (a.numberAttempts >= 3 OR a.expirationDate < CURRENT_DATE) AND a.type ='ACCOUNT_CREATION'")
    void deleteAccountCreationActivationLinks();
}
