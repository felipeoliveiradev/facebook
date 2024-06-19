package com.facebook.modules.Auth.modules.Token.domain;


import com.facebook.modules.Flup.system.required.helpers.Identifier;

import java.util.Objects;
import java.util.UUID;

public class TokenID extends Identifier {

    private final String value;


    public TokenID(final String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static TokenID unique() {
        return TokenID.from(UUID.randomUUID());
    }

    public static TokenID from(final String anID) {
        return new TokenID(anID);
    }

    public static TokenID from(final UUID anID) {
        return new TokenID(anID.toString().toLowerCase());
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final TokenID that = (TokenID) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
