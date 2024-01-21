package com.wevioo.pi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wevioo.pi.domain.entity.request.referential.ConfigurationSearchField;
import com.wevioo.pi.domain.enumeration.InvestmentType;
import com.wevioo.pi.domain.enumeration.UserTypeEnum;

public interface ConfigurationSearchFieldRepository extends JpaRepository<ConfigurationSearchField, Long> {
	/**
	 * 
	 * @param type
	 * @param userType
	 * @return
	 */

	ConfigurationSearchField findByInvestmentTypeAndUserType(InvestmentType type, UserTypeEnum userType);

}
