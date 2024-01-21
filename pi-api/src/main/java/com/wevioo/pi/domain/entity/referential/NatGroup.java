package com.wevioo.pi.domain.entity.referential;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * NatSection Model DataTable
 *
 * @author  knh
 *
 */
@Entity
@Table(name = "R_NAT_GROUPE")
@Getter
@Setter
public class NatGroup implements Serializable {

        /**
       * Serial Number
        */
         private static final long serialVersionUID = -8115650653859707569L;



        /**
         *  Nat  Group id.
         */
        @Id
        @Column(name = "KNAT_G", updatable = false, nullable = false)
        private String id;


        /**
         *   Nat Group label;
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
}
