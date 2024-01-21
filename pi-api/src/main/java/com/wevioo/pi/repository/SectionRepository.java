package com.wevioo.pi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wevioo.pi.domain.entity.config.Section;
import com.wevioo.pi.domain.enumeration.DeclarationNature;
import com.wevioo.pi.domain.enumeration.InvestmentType;
import com.wevioo.pi.domain.enumeration.OperationType;

/**
 * Manage DAO module of Section Entity
 * 
 */
public interface SectionRepository extends JpaRepository<Section, Long> {

	/**
	 * Search list {@link Section} given by parameter.
	 * 
	 * @param declarationNature {@link DeclarationNature}
	 * @param investmentType    {@link InvestmentType}
	 * @param operationType     {@link OperationType}
	 * @return list of Section
	 */
	List<Section> findByDeclarationNatureAndInvestmentTypeAndOperationType(DeclarationNature declarationNature,
			InvestmentType investmentType, OperationType operationType);

	List<Section> findByOperationType(OperationType operationType);
}
