package com.epam.training.sportsbetting.validation;

public class ExceptionRepresentation {

    private String message;

    public ExceptionRepresentation(Exception exception) {
        this.message = exception.getMessage();
    }

    public String getMessage() {
        return message;
    }
}