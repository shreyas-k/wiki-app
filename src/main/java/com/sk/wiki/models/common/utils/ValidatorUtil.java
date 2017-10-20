package com.sk.wiki.models.common.utils;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.sk.wiki.models.common.lang.ValidationException;

public class ValidatorUtil {
	public static <T> void validateEntityFields(final Validator validator, final T entity) {
		final Set<ConstraintViolation<T>> violations = validator.validate(entity);
		if (!violations.isEmpty()) {
			final Set<String> violationMessages = violations
					.stream()
					.map(v -> String.format("%s %s", v.getPropertyPath(), v.getMessage()))
					.collect(Collectors.toSet());
			final ValidationException exception = new ValidationException(
					String.format("%s contains invalid fields", entity.getClass().getSimpleName()));
			exception.setErrors(violationMessages);
			throw exception;
		}
	}
}