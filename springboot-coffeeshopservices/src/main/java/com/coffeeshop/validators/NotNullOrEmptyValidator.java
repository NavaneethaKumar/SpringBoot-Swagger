package com.coffeeshop.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotNullOrEmptyValidator implements ConstraintValidator<NotNullOrEmpty, String> {

	@Override
	public void initialize(final NotNullOrEmpty arg0) {

	}

	@Override
	public boolean isValid(final String obj, final ConstraintValidatorContext constraintContext) {
		if (obj == null || obj.isEmpty()) {
			return false;
		}
		return true;
	}

}
