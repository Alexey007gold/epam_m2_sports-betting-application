package com.epam.training.sportsbetting.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This DTO is returned when a form has not passed a validation
 */
public class ValidationFailure  {

    private Map<String, List<String>> errors = new HashMap<>();

    private String errorMessage;

    public void addValidationError(String field, String defaultMessage) {
        List<String> fieldErrors = errors.get(field);
        if (fieldErrors == null) {
            fieldErrors = new ArrayList<>();
        }

        fieldErrors.add(defaultMessage);
        errors.put(field, fieldErrors);
    }

    public Map<String, List<String>> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, List<String>> errors) {
        this.errors = errors;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}