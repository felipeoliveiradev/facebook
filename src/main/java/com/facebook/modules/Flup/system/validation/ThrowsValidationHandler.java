package com.facebook.modules.Flup.system.validation;


import com.facebook.modules.Flup.system.validation.Exceptions.DomainException;
import com.facebook.modules.Flup.system.validation.handlers.ValidationHandler;

import java.util.List;

public class ThrowsValidationHandler implements ValidationHandler {
    @Override
    public ValidationHandler append(final Error anError) {
        throw DomainException.with(anError);
    }

    @Override
    public void appendList(final List<Error> anError) {
        throw DomainException.with(anError);
    }

    @Override
    public void append(final ValidationHandler anHandler) {
        throw DomainException.with(anHandler.getErrors());
    }

    @Override
    public ValidationHandler validate(Validation aValidation) {
        try {
            aValidation.validate();
        } catch (Exception ex) {
            throw DomainException.with(new Error(ex.getMessage()));
        }
        return this;
    }

    @Override
    public List<Error> getErrors() {
        return List.of();
    }
}