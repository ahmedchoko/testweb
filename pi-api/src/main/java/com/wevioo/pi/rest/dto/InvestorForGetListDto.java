package com.wevioo.pi.rest.dto;


import com.wevioo.pi.annotation.ExcelPdfHeader;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class InvestorForGetListDto implements Serializable {

    /**
     * Serial Number
     */
    private static final long serialVersionUID = 4751568478447203565L;

    /**
     * Investor's id
     */
    @ExcelPdfHeader("ID Systeme")
    private String id;

    /**
     * Investor type
     */
    @ExcelPdfHeader("Type Investisseur")
    private String investorType;

    /**
     * Investor's fullName
     */
    @ExcelPdfHeader("Nom Prenom")
    private String fullName;

    /**
     * Investor's creationDate
     */
    @ExcelPdfHeader("Date Creation Compte")
    private Date creationDate;

}
