package com.facebook.modules.Flup.system.validation.handlers;


import com.facebook.modules.Flup.system.validation.Error;
import com.facebook.modules.Flup.system.validation.Exceptions.DomainException;

import java.util.List;

public class ThrowsValidationHandler implements ValidationHandler {
    @Override
    public ValidationHandler append(final Error anError) {
        throw DomainException.with(anError);
    }

    @Override
    public void appendList(List<Error> anError) {
        throw DomainException.with(anError);
    }

    @Override
    public void append(final ValidationHandler anHandler) {
        throw DomainException.with(anHandler.getErrors());
    }

    @Override
    public <T> T validate(Validation<T> aValidation) {
        try {
            return aValidation.validate();
        } catch (Exception ex) {
            throw DomainException.with(new Error(ex.getMessage()));
        }
    }

    @Override
    public List<Error> getErrors() {
        return List.of();
    }
}