package com.facebook.modules.Flup.system.validation.handlers;

import com.facebook.modules.Flup.system.validation.Error;
import com.facebook.modules.Flup.system.validation.Exceptions.DomainException;

import java.util.ArrayList;
import java.util.List;

public class Notification implements ValidationHandler {

    private final List<Error> errors;

    public Notification(final List<Error> errors) {
        this.errors = errors;
    }

    public static Notification create() {
        return new Notification(new ArrayList<>());
    }

    public static Notification create(final Error anError) {
        return new Notification(new ArrayList<>()).append(anError);
    }

    public static Notification create(final Throwable t) {
        return create(new TreatmentHandlers(t.getMessage()).exec());
    }

    @Override
    public Notification append(final Error anError) {
        this.errors.add(anError);
        return this;
    }

    @Override
    public void appendList(final List<Error> anError) {
        this.errors.addAll(anError);
    }


    @Override
    public void append(final ValidationHandler anHandler) {
        this.errors.addAll(anHandler.getErrors());
    }

    @Override
    public <T> T validate(final Validation<T> aValidation) {
        try {
            return aValidation.validate();
        } catch (final DomainException ex) {
            this.errors.addAll(ex.getErrors());
        } catch (final Throwable t) {
            this.errors.add(new Error(t.getMessage()));
        }
        return null;
    }

    @Override
    public List<Error> getErrors() {
        return this.errors;
    }
}
