package com.wevioo.pi.service;

import java.util.List;
import java.util.Map;

import com.wevioo.pi.domain.entity.referential.Parameter;

/**
 * This interface manage {@link Parameter} entity.
 *
 * @author kad
 *
 */
public interface ParameterService {

	/**
	 * this method returns {@link Parameter} given by.
	 *
	 * @param parameterKey
	 *            {@link Parameter} key.
	 * @return {@link Parameter}.

	 */
	Parameter getByKey(String parameterKey) ;

	/**
	 * this method return {@link Parameter} list given by prefix.
	 *
	 * @param parameterPrefix
	 *            {@link Parameter} prefix.
	 * @return {@link Parameter} list.
	 */
	List<Parameter> getByPrefix(String parameterPrefix);

	Parameter getByKeyAndPrefix(String parameterKey, String parameterPrefix);
	/**
	 * this method return {@link Parameter} list given by prefix.
	 *
	 * @param parameterPrefix
	 *            {@link Parameter} prefix.
	 * @return {@link Parameter} list.
	 */
	Map<String, Parameter> getMapParamByPrefix(String parameterPrefix);

}
