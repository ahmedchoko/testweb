package com.wevioo.pi.exception;

import com.wevioo.pi.common.ApplicationConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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

	private ExceptionResult() {
		super();

	}

	public static ResponseEntity<ConflictResponse> generateConflictException(BindingResult result, String path) {
		if (result.hasErrors()) {
			String bodyMessage = "Validation failed for operator request.";
			FieldError error = result.getFieldErrors().get(0);
			String errorMessage = error.getDefaultMessage();
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(new ConflictResponse(path, errorMessage, bodyMessage));
		}
		return null;
	}

	public static ResponseEntity<ConflictResponse> generateConflictException(BindingResult result, String path,
			HttpStatus httpStatus) {
		if (result.hasErrors()) {
			String bodyMessage = "Validation failed for operator request.";
			FieldError error = result.getFieldErrors().get(0);
			String errorMessage = error.getDefaultMessage();
			return ResponseEntity.status(httpStatus).body(new ConflictResponse(path, errorMessage, bodyMessage));
		}
		return null;
	}

	public static ResponseEntity<BadRequestResponse> generateBadRequestException(Errors result, String path,
			String inputField) {
		if (result == null) {
			return ResponseEntity.badRequest()
					.body(new BadRequestResponse(path, ApplicationConstants.FAILED_VALIDATION, inputField));
		}

		if (result.hasErrors()) {
			Map<String, List<String>> errors = new HashMap<>();
			Set<String> processedFields = new HashSet<>();
			String message = ApplicationConstants.FAILED_VALIDATION;
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

}
