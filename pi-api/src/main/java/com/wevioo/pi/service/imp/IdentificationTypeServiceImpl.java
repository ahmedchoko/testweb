package com.wevioo.pi.service.imp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.domain.entity.referential.IdentificationType;
import com.wevioo.pi.exception.DataNotFoundException;
import com.wevioo.pi.repository.IdentificationTypeRepository;
import com.wevioo.pi.service.IdentificationTypeService;

@Service
public class IdentificationTypeServiceImpl implements IdentificationTypeService {

	/**
	 * Injected bean {@link IdentificationTypeRepository}
	 */
	@Autowired
	IdentificationTypeRepository identificationTypeRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<IdentificationType> findAllIdentifier() {
		return identificationTypeRepository.findAllByIsActiveTrue();
	}

	@Override
	public String findPatternByIdentifier(String identifier) {
		return identificationTypeRepository.getPatternByType(identifier);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<IdentificationType> findById(Long id) {
		return identificationTypeRepository.findById(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IdentificationType findByType(String type) {
		return identificationTypeRepository.findByType(type).orElseThrow(
				() -> new DataNotFoundException(ApplicationConstants.ERROR_IDENTIFICATION_TYPE_NOT_FOUND_REF,
						"NO IDENTIFICATION TYPE FOUND WITH LABEL " + type));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean existsByType(String type) {
		return identificationTypeRepository.existsByType(type);
	}
}
