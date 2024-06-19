package com.facebook.modules.Post.domain;


import com.facebook.modules.Flup.system.required.helpers.Identifier;

import java.util.Objects;
import java.util.UUID;

public class PostID extends Identifier {

    private final String value;


    public PostID(final String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static PostID unique() {
        return PostID.from(UUID.randomUUID());
    }

    public static PostID from(final String anID) {
        return new PostID(anID);
    }

    public static PostID from(final UUID anID) {
        return new PostID(anID.toString().toLowerCase());
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final PostID that = (PostID) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
