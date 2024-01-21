package com.wevioo.pi.domain.entity.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
/**
 * BctAgent Model DataTable
 *
 * @author  knh
 *
 */
@Table(name = "PI004T_BCT_AGENT")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BctAgent  extends  User {
	/**
	 * Serial Number
	 */
    private static final long serialVersionUID = 4202356554970432274L;
	/**
     * registration Number
     */
    @Column(name = "PI004_REGISTRATION_NUMBER" )
    private String  registrationNumber;
    /**
     * General Management Assignment
     */
    @Column(name = "PI004_GENERAL_MANAGEMENT_ASSIGNMENT")
    private String generalManagementAssignment ;
    /**
     * service Assignment
     */
    @Column(name = "PI004_SERVICE_ASSIGNMENT")
    private  String serviceAssignment;
    /**
     * IS Admin
     */
    @Column(name = "PI004_IS_ADMIN")
    private Boolean isAdmin;
}
