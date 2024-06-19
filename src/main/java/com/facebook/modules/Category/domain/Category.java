package com.facebook.modules.Category.domain;


import com.facebook.modules.Flup.system.required.helpers.AggregateRoot;
import com.facebook.modules.Flup.system.validation.handlers.ValidationHandler;

import java.time.Instant;
import java.util.Objects;

public class Category extends AggregateRoot<CategoryID> implements Cloneable {
    private final Instant createdAt;
    private String name;
    private boolean active;
    private Instant updatedAt;
    private Instant deletedAt;

    private Category(
            final CategoryID anId,
            final String nameField,
            final boolean isActive,
            final Instant createdDateField,
            final Instant updatedDateField,
            final Instant deletedDateField
    ) {
        super(anId);
        this.name = nameField;
        this.active = isActive;
        this.createdAt = Objects.requireNonNull(createdDateField, "'createdAt' should not be null");
        this.updatedAt = Objects.requireNonNull(updatedDateField, "'updatedAt' should not be null");
        this.deletedAt = deletedDateField;
    }


    public static Category newCategory(
            final String nameField,
            final boolean isActive
    ) {
        final var id = CategoryID.unique();
        final var now = Instant.now();
        final var deletedAt = isActive ? null : now;
        return new Category(
                id,
                nameField,
                isActive,
                now,
                now,
                deletedAt
        );
    }

    public static Category with(
            final Category aCategory
    ) {
        return with(
                aCategory.id,
                aCategory.name,
                aCategory.active,
                aCategory.createdAt,
                aCategory.updatedAt,
                aCategory.deletedAt
        );
    }

    public static Category with(
            final CategoryID anId,
            final String name,
            final boolean active,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        return new Category(
                anId,
                name,
                active,
                createdAt,
                updatedAt,
                deletedAt

        );
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new CategoryValidator(this, handler).validate();
    }

    public void deactivate() {
        if (getDeletedAt() == null) {
            this.deletedAt = Instant.now();
        }
        this.active = false;
        this.updatedAt = Instant.now();
    }

    public void activate() {

        this.deletedAt = null;
        this.active = true;
        this.updatedAt = Instant.now();
    }


    public Category update(
            final String nameField,
            final boolean isActive) {

        if (isActive) {
            this.activate();
        } else {
            this.deactivate();
        }
        this.name = nameField;
        this.updatedAt = Instant.now();
        return this;
    }

    public CategoryID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    @Override
    public Category clone() {
        try {
            return (Category) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }


}
