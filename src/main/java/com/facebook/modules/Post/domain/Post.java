package com.facebook.modules.Post.domain;


import com.facebook.modules.Category.domain.CategoryID;
import com.facebook.modules.Flup.system.required.helpers.AggregateRoot;
import com.facebook.modules.Flup.system.required.utils.InstantUtils;
import com.facebook.modules.Flup.system.validation.handlers.ValidationHandler;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Post extends AggregateRoot<PostID> implements Cloneable {
    private final Instant createdAt;
    private String name;
    private String description;
    private List<CategoryID> categories;
    private boolean active;
    private Instant updatedAt;
    private Instant deletedAt;

    private Post(
            final PostID anId,
            final String nameField,
            final String descriptionField,
            final List<CategoryID> categoriesField,
            final boolean isActive,
            final Instant createdDateField,
            final Instant updatedDateField,
            final Instant deletedDateField
    ) {
        super(anId);
        this.name = nameField;
        this.description = descriptionField;
        this.categories = categoriesField;
        this.active = isActive;
        this.createdAt = Objects.requireNonNull(createdDateField, "'createdAt' should not be null");
        this.updatedAt = Objects.requireNonNull(updatedDateField, "'updatedAt' should not be null");
        this.deletedAt = deletedDateField;
    }

    public static Post newPost(
            final String nameField,
            final String descriptionField,
            final List<CategoryID> categoriesField,
            final boolean isActive
    ) {
        final var id = PostID.unique();
        final var now = Instant.now();
        final var deletedAt = isActive ? null : now;
        return new Post(
                id,
                nameField,
                descriptionField,
                categoriesField,
                isActive,
                now,
                now,
                deletedAt
        );
    }

    public static Post with(
            final Post aPost
    ) {
        return with(
                aPost.id,
                aPost.name,
                aPost.description,
                aPost.categories,
                aPost.active,
                aPost.createdAt,
                aPost.updatedAt,
                aPost.deletedAt
        );
    }

    public static Post with(
            final PostID anId,
            final String name,
            final String description,
            final List<CategoryID> categories,
            final boolean active,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        return new Post(
                anId,
                name,
                description,
                new ArrayList<>(categories),
                active,
                createdAt,
                updatedAt,
                deletedAt

        );
    }

    public Post addCategory(final CategoryID categoryIDField) {
        if (categoryIDField == null) {
            return this;
        }
        this.categories.add(categoryIDField);
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public Post removeCategory(final CategoryID categoryIDField) {
        if (categoryIDField == null) {
            return this;
        }
        this.categories.remove(categoryIDField);
        this.updatedAt = InstantUtils.now();
        return this;
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new PostValidator(this, handler).validate();
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


    public Post update(
            final String nameField,
            final String descriptionField,
            final List<CategoryID> categoriesList,
            final boolean isActive) {

        if (isActive) {
            this.activate();
        } else {
            this.deactivate();
        }
        this.name = nameField;
        this.description = descriptionField;
        this.categories = new ArrayList<>(categoriesList != null ? categoriesList : Collections.emptyList());
        this.updatedAt = Instant.now();
        return this;
    }

    public PostID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<CategoryID> getCategories() {
        return Collections.unmodifiableList(categories);
    }

    public String getDescription() {
        return description;
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
    public Post clone() {
        try {
            return (Post) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }


}
