package com.wevioo.pi.repository;

import com.wevioo.pi.domain.entity.account.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



/**
 * Repository provides methods to access {@link User}
 *
 * @author shl
 */
@Repository
public interface UserRepository extends CrudRepository<User, String> {


    /**
     * Find user by email and is Activate
     *
     * @param isActive user's isActive
     * @param email    user's email
     * @return User
     */
    User findByLoginIgnoreCaseAndIsActive(String email, Boolean isActive);
    User findByLogin(String username);

    /**
     * User exists by email
     *
     * @param email user's email
     * @return true/false
     */
    boolean existsByLoginIgnoreCase(String email);

    /**
     *  User exists by email and id not
     * @param email user email
     * @param id user id
     * @return true or false
     */
    boolean existsByLoginIgnoreCaseAndAndIdNot(String email , String id);

    @Modifying
    @Query("update User u set u.isActive = true where u.id =:id")
    void setEnabled(@Param("id") String id);


}
