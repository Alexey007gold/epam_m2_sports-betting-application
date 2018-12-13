package com.epam.training.sportsbetting.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.List;

/**
 * Creates ValidationFailure DTO for validation Errors object
 */
public final class ValidationFailureBuilder {

    private ValidationFailureBuilder() {
    }

    public static ValidationFailure fromBindingErrors(Errors errors) {
        ValidationFailure failure = new ValidationFailure();
        failure.setErrorMessage("Validation has failed. " + errors.getErrorCount() + " error(s)");

        List<FieldError> fieldErrorList = errors.getFieldErrors();
        for (FieldError fieldError : fieldErrorList) {
            failure.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return failure;
    }
}