package com.epam.training.sportsbetting.controller;

import com.epam.training.sportsbetting.validation.ExceptionRepresentation;
import com.epam.training.sportsbetting.validation.ValidationFailure;
import com.epam.training.sportsbetting.validation.ValidationFailureBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationFailure bindException(BindException e) {
        return ValidationFailureBuilder.fromBindingErrors(e.getBindingResult());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionRepresentation> runtimeException(RuntimeException e) {
        return new ResponseEntity<>(new ExceptionRepresentation(e), resolveAnnotatedResponseStatus(e));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ExceptionRepresentation> runtimeException(IllegalStateException e) {
        return new ResponseEntity<>(new ExceptionRepresentation(e), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionRepresentation> runtimeException(IllegalArgumentException e) {
        return new ResponseEntity<>(new ExceptionRepresentation(e), HttpStatus.BAD_REQUEST);
    }

    private HttpStatus resolveAnnotatedResponseStatus(Exception exception) {
        ResponseStatus annotation = findMergedAnnotation(exception.getClass(), ResponseStatus.class);
        return annotation == null ? HttpStatus.INTERNAL_SERVER_ERROR : annotation.value();
    }

}