package com.facebook.modules.Flup.spring.exception;

import com.facebook.modules.Flup.system.validation.Error;
import com.facebook.modules.Flup.system.validation.Exceptions.DomainException;
import com.facebook.modules.Flup.system.validation.Exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<?> handlerNotFoundException(
            final DomainException ex
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiError.from(ex));
    }


    @ExceptionHandler(value = DomainException.class)
    public ResponseEntity<?> handlerDomainException(
            final DomainException ex
    ) {
        return ResponseEntity.unprocessableEntity().body(ApiError.from(ex));
    }


    record ApiError(String message, List<Error> errors) {
        static ApiError from(final DomainException ex) {
            return new ApiError(ex.getMessage(), ex.getErrors());
        }
    }
}
