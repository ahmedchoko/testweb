package com.wevioo.pi.rest.dto;

import com.wevioo.pi.annotation.ExcelPdfHeader;
import com.wevioo.pi.domain.enumeration.TypeAdministrator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankerForGetAllDto {


    /**
     * bankName
     */
    @ExcelPdfHeader("Intermédiaire Agrée")
    private String bankName;
    /**
     * id
     */
    @ExcelPdfHeader("ID Systeme")
    private String id;
    /**
     * firstName
     */
    @ExcelPdfHeader("Prenom")
    private String firstName;
    /**
     * lastName
     */
    @ExcelPdfHeader("Nom")
    private String lastName;
    /**
     * typeAdministrator
     */
    @ExcelPdfHeader("Type Admin")
    private TypeAdministrator typeAdministrator;
    /**
     * creationDate
     */
    @ExcelPdfHeader("Date Creation Compte")
    private Date creationDate;

}
