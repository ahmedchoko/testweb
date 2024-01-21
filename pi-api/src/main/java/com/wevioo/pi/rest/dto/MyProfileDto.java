package com.wevioo.pi.rest.dto;

import com.wevioo.pi.domain.enumeration.PersonTypeEnum;
import com.wevioo.pi.domain.enumeration.UserTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class MyProfileDto implements Serializable {

    /**
     * Serial Number
     */
    private static final long serialVersionUID = -3203385865346291401L;

    /**
     * Id
     */
    private String id;

    /**
     * User first name
     */
    private String firstName;

    /**
     * User last name
     */
    private String lastName;

    /**
     * socialReason
     */
    private String socialReason;

    /**
     * login of the User
     */
    private String login;
    /**
     * Phone of the User
     */
    private String cellPhone;

    /**
     * Email of the User
     */
    private String email;

    /**
     *  user Type
     */
    private UserTypeEnum  userType;

    /**
     *  is Admin
     */
  private  Boolean isAdmin;

    /**
     * Person type
     */

  private  PersonTypeEnum  personType;

    /**
     * BankLabel for bankers and admin bankers.
     */
  private String bankLabel;



    /**
     *
     * @param id
     * @param firstName
     * @param lastName
     * @param login
     */
    public MyProfileDto(String id, String firstName, String lastName, String login,
                            UserTypeEnum userTypeEnum , Boolean isAdmin) {
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.userType  = userTypeEnum;
        this.isAdmin = isAdmin;
    }

    public MyProfileDto(String id, String firstName, String lastName, String login,
                        UserTypeEnum userTypeEnum , Boolean isAdmin, String bankLabel) {
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.userType  = userTypeEnum;
        this.isAdmin = isAdmin;
        this.bankLabel = bankLabel;
    }

    public MyProfileDto(String id, String firstName, String lastName, String login,
                        UserTypeEnum userTypeEnum ,   String socialReason , PersonTypeEnum personTypeEnum ) {
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.userType  = userTypeEnum;
        this.personType = personTypeEnum;
        this.socialReason = socialReason;

    }



}
