package com.facebook.modules.Flup.system.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ValidationField<T> {

    private final String field;
    private final T input;
    private final List<Error> validation;

    public ValidationField(String field, T input) {
        this.field = field;
        this.input = input;
        this.validation = new ArrayList<>();
    }

    public ValidationField<T> Null() {
        if (input == null) {
            validation.add(new Error(String.format("'%s' should not be null", field)));
        }
        return this;
    }

    public ValidationField<T> Empty() {
        if (input == null || (input instanceof String && ((String) input).isEmpty()) ||
                (input instanceof List && ((List<?>) input).isEmpty()) ||
                (input instanceof Object[] && ((Object[]) input).length == 0) ||
                (input instanceof boolean[] && ((boolean[]) input).length == 0)) {
            validation.add(new Error(String.format("'%s' should not be empty", field)));
        }
        return this;
    }

    public ValidationField<T> Min(int minLength) {
        if (input != null && input instanceof String && ((String) input).length() < minLength) {
            validation.add(new Error(String.format("'%s' should have at least %d characters", field, minLength)));
        }
        return this;
    }

    public ValidationField<T> Max(int maxLength) {
        if (input != null && input instanceof String && ((String) input).length() > maxLength) {
            validation.add(new Error(String.format("'%s' should have at most %d characters", field, maxLength)));
        }
        return this;
    }

    public ValidationField<T> Custom(String regex, String message) {
        if (input != null && input instanceof String && !((String) input).isEmpty()) {
            if (!Pattern.matches(regex, (String) input)) {
                validation.add(new Error(message));
            }
        }
        return this;
    }

    public ValidationField<T> Email() {
        return Custom("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$", String.format("'%s' should be a valid email", field));
    }

    public ValidationField<T> Password() {
        return Custom("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", String.format("'%s' should be a valid password", field));
    }

    public ValidationField<T> Username() {
        return Custom("^[a-zA-Z0-9_-]{3,16}$", String.format("'%s' should be a valid username", field));
    }

    public ValidationField<T> Url() {
        return Custom("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]$", String.format("'%s' should be a valid URL", field));
    }

    public ValidationField<T> Name() {
        return Custom("[A-Z][a-z]+( [A-Z][a-z]+)*", String.format("'%s' should be a valid name", field));
    }

    public ValidationField<T> Document(String type) {
        if ("CPF".equals(type)) {
            return Custom("^[0-9]{11}$", String.format("'%s' should be a valid CPF", field));
        } else if ("CNPJ".equals(type)) {
            return Custom("^[0-9]{14}$", String.format("'%s' should be a valid CNPJ", field));
        } else if ("CPF&CNPJ".equals(type)) {
            return Custom("^(?:[0-9]{11}|[0-9]{14})$", String.format("'%s' should be a valid CPF or CNPJ", field));
        } else {
            return this;
        }
    }

    public ValidationField<T> Address() {
        if (input != null && input instanceof String address) {
            if (address.length() < 5) {
                validation.add(new Error(String.format("'%s' should have at least 5 characters", field)));
            }
        } else {
            validation.add(new Error(String.format("'%s' should be a valid address", field)));
        }
        return this;
    }

    public ValidationField<T> Coin() {
        if (input != null && input instanceof Double) {
            double amount = (Double) input;
            if (amount <= 0) {
                validation.add(new Error(String.format("'%s' should be a positive amount", field)));
            }
        } else {
            validation.add(new Error(String.format("'%s' should be a valid amount", field)));
        }
        return this;
    }

    public ValidationField<T> PhoneNumber() {
        if (input != null && input instanceof String phoneNumber) {
            if (!Pattern.matches("\\d{10,12}", phoneNumber)) {
                validation.add(new Error(String.format("'%s' should be a valid phone number", field)));
            }
        } else {
            validation.add(new Error(String.format("'%s' should be a valid phone number", field)));
        }
        return this;
    }

    public ValidationField<T> Integer() {
        if (input != null && input instanceof Integer) {
        } else {
            validation.add(new Error(String.format("'%s' should be an integer", field)));
        }
        return this;
    }

    public ValidationField<T> Decimal() {
        if (input != null && (input instanceof Double || input instanceof Float)) {
        } else {
            validation.add(new Error(String.format("'%s' should be a decimal number", field)));
        }
        return this;
    }

    public ValidationField<T> CustomValidation(boolean isValid, String message) {
        if (!isValid) {
            validation.add(new Error(message));
        }
        return this;
    }

    public List<Error> validate() {
        return validation;
    }
}
