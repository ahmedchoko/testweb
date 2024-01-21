package com.wevioo.pi.repository;

import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.wevioo.pi.domain.entity.referential.Parameter;

/**
 * Interface DAO of the {@link Parameter}.
 *
 * @author kad
 *
 */
public interface ParameterRepository extends JpaRepository<Parameter, Long> {


	/**
	 * this methods search a parameter which start by.
	 *
	 * @param searchTerm
	 *            the attribute searchTerm.
	 * @return {@link Parameter}'s list.
	 */
	@Query(value = "SELECT parameter FROM Parameter parameter WHERE parameter.parameterKey LIKE :searchTerm%")
	List<Parameter> getParameterByPrefix(@Param("searchTerm") String searchTerm);
	
	/**
	 * this methods search a parameter which parameter key.
	 *
	 * @param parameterKey
	 *            the attribute searchTerm.
	 * @return {@link Parameter}.
	 */
	Parameter findByParameterKey(String parameterKey);
	
	/**
	 * this methods searches a parameter with a parameter key with LockModeType.PESSIMISTIC_WRITE
	 * 
	 * @param parameterKey
	 *            parameter to look for.
	 * @return parameter value
	 */

	@Query("SELECT parameter FROM Parameter parameter WHERE parameter.parameterKey = :key")
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Parameter findByParameterKeyPessimisticWrite(@Param("key") String parameterKey);
	
	/**
	 * this methode search a parameter with parameter value
	 * @Param parameterValue
	 * @return {@link Parameter}
	 */
	Parameter findByParameterValue(String value);

	Parameter findByParameterKeyAndParameterPattern(String parameterKey, String parameterPattern);
}
