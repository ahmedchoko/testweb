package com.wevioo.pi.service.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.domain.entity.referential.Parameter;
import com.wevioo.pi.exception.DataNotFoundException;
import com.wevioo.pi.repository.ParameterRepository;
import com.wevioo.pi.service.ParameterService;

import lombok.extern.slf4j.Slf4j;


/**
 * This class implements {@link ParameterService}.
 *
 * @author kad
 *
 */
@Service("parameterService")
@Slf4j
public class ParameterServiceImpl implements ParameterService {

	/**
	 * Inject {@link ParameterRepository} bean.
	 */
	@Autowired
	private ParameterRepository parameterRepository;

	/**
	 * {@inheritDoc}.
	 */
	@Override
	public Parameter getByKey(final String parameterKey)  {
		log.info("Start search paramter  with Key:[" + parameterKey + "]");
		Parameter parameter = parameterRepository.findByParameterKey(parameterKey);
		if (parameter == null) {
			log.error("No Paramter has been found with the corresponding Key:[" + parameterKey + "]");
			throw new DataNotFoundException(ApplicationConstants.ERROR_PARAMETER_NOT_FOUND,
					"Parameter not found with key: " +parameterKey);
		}

		return parameter;

	}

	/**
	 * {@inheritDoc}.
	 */
	@Override
	public List<Parameter> getByPrefix(final String parameterPrefix) {
		log.info("Start search paramter list with prefix:[" + parameterPrefix + "]");
		return  parameterRepository.getParameterByPrefix(parameterPrefix);
		
	}

	@Override
	public Parameter getByKeyAndPrefix(String parameterKey, String parameterPrefix) {
		log.info("Start search paramter  with Key:[" + parameterKey + "]");
		Parameter parameter = parameterRepository.findByParameterKeyAndParameterPattern(parameterKey, parameterPrefix);
		if (parameter == null) {
			log.error("No Paramter has been found with the corresponding Key:[" + parameterKey + "] and Prefix:["+parameterPrefix+"]");
			throw new DataNotFoundException(ApplicationConstants.ERROR_PARAMETER_NOT_FOUND,
					"Parameter not found with key: " +parameterKey+ " and prefix: "+parameterPrefix);
		}
		return parameter;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Parameter> getMapParamByPrefix(String parameterPrefix) {
		List<Parameter> params = parameterRepository.getParameterByPrefix(parameterPrefix);
		Map<String, Parameter> resultMap = new HashMap<>();
		for(Parameter parameter : params) {
			resultMap.put(parameter.getParameterKey(), parameter);
		}
		return resultMap;
	}

}
