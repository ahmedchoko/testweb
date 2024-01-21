package com.wevioo.pi.domain.entity.referential;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Nat    CLASS  Model DataTable
 *
 * @author  knh
 *
 */
@Entity
@Table(name = "R_NAT_CLASSE")
@Getter
@Setter
public class NatClass implements Serializable {

    /**
     * Serial Number
     */
    private static final long serialVersionUID = -8115650653859707569L;



    /**
     *  Nat  Group id.
     */
    @Id
    @Column(name = "KNAT_C", updatable = false, nullable = false)
    private String id;


    /**
     *   Nat  class  label;
     */
    @Column(name = "LIBELLE" , nullable = false)
    private String  label;
    /**
     *  Nat  section id
     */
    @Column( name =  "KNAT_S" , nullable = false)
    private  String natSectionId;

    /**
     *  Nat sub   section id
     */
    @Column( name =  "KNAT_SS" , nullable = false)
    private  String natSubSectionId;


    /**
     *  Nat  group  id
     */
    @Column( name =  "KNAT_G" , nullable = false)
    private  String groupId;



}
