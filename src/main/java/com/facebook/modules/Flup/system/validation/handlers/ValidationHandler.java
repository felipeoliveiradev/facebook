package com.facebook.modules.Flup.system.validation.handlers;

import com.facebook.modules.Flup.system.validation.Error;

import java.util.List;

public interface ValidationHandler {

    ValidationHandler append(Error anError);


    void appendList(List<Error> anError);

    void append(ValidationHandler anHandler);

    <T> T validate(Validation<T> aValidation);

    List<Error> getErrors();

    default boolean hasError() {
        return getErrors() != null && !getErrors().isEmpty();
    }

    default Error firstError() {
        if (getErrors() != null && !getErrors().isEmpty()) {
            return getErrors().get(0);
        } else {
            return null;
        }
    }

    interface Validation<T> {
        T validate();
    }

}