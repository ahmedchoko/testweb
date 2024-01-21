package com.wevioo.pi.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.wevioo.pi.domain.entity.config.Section;
import com.wevioo.pi.domain.enumeration.DeclarationNature;
import com.wevioo.pi.domain.enumeration.InvestmentType;
import com.wevioo.pi.domain.enumeration.OperationType;
import com.wevioo.pi.rest.dto.DownloadDocumentDto;
import com.wevioo.pi.rest.dto.SectionConfigDto;

import net.sf.jasperreports.engine.JRException;

public interface SectionService {

	/**
	 * Search list {@link Section} given by parameter.
	 * 
	 * @param declarationNature {@link DeclarationNature}
	 * @param investmentType    {@link InvestmentType}
	 * @param operationType     {@link OperationType}
	 * @param idForm            form's id
	 * @return list of Section
	 */

	List<SectionConfigDto> findByDeclarationNatureAndInvestmentTypeAndOperationType(DeclarationNature declarationNature,
			InvestmentType investmentType, OperationType operationType, String idForm);

	/**
	 * 
	 * @param declarationNature
	 * @param investmentType
	 * @param operationType
	 * @return DownloadDocumentDto
	 * @throws JRException
	 * @throws FileNotFoundException
	 */
	DownloadDocumentDto exportDocumentsByDeclarationNatureAndInvestmentTypeAndOperationType(DeclarationNature declarationNature, InvestmentType investmentType, OperationType operationType);


	
	/** export Sheet Investment By DeclarationNature InvestmentType And OperationType
	 * 
	 * @param declarationNature
	 * @param investmentType
	 * @param operationType
	 * @param idForm
	 * @return
	 * @throws JRException 
	 * @throws FileNotFoundException 
	 * @throws IOException 
	 */
	DownloadDocumentDto exportSheetDocumentsByDeclarationNatureAndInvestmentTypeAndOperationType(
			DeclarationNature declarationNature, InvestmentType investmentType, OperationType operationType,
			String idDirectInvest) throws FileNotFoundException, JRException, IOException;

}
