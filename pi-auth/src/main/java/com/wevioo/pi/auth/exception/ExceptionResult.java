package com.wevioo.pi.auth.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ExceptionResult {

	private static final String FAILED_VALIDATION = "FAILED_VALIDATION";

	public static ResponseEntity<BadRequestResponse> generateBadRequestException(Errors result, String path) {
		if (result.hasErrors()) {
			Map<String, List<String>> errors = new HashMap<>();
			Set<String> processedFields = new HashSet<>();
			String message = FAILED_VALIDATION;
			for (FieldError error : result.getFieldErrors()) {
				List<String> messages = new ArrayList<>();
				List<FieldError> filtredErrors = new ArrayList<>();
				String fieldName = error.getField();
				if (processedFields.contains(fieldName)) {
					continue;
				}
				if (!errors.containsKey(fieldName)) {
					errors.put(fieldName, new ArrayList<>());
				}
				filtredErrors.addAll(result.getFieldErrors().stream().filter(err -> err.getField().equals(fieldName))
						.collect(Collectors.toList()));
				for (FieldError field : filtredErrors) {
					messages.add(field.getDefaultMessage());
				}
				errors.get(fieldName).addAll(messages);
				processedFields.add(fieldName);
			}
			return ResponseEntity.badRequest().body(new BadRequestResponse(path, errors, message));
		}
		return null;
	}
	/**
	 * private constructor
	 */
	private ExceptionResult() {

	}

}
