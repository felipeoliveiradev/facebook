package com.facebook.modules.Post.infra;

import com.facebook.modules.Category.domain.CategoryID;
import com.facebook.modules.Post.domain.Post;
import com.facebook.modules.Post.domain.PostID;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Post")
public class PostDataEntity {

    @Id
    private String id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "description", length = 255)
    private String description;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<PostCategoryEntity> categories;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant updatedAt;

    @Column(name = "deleted_at", columnDefinition = "DATETIME(6)")
    private Instant deletedAt;

    public PostDataEntity() {
    }

    private PostDataEntity(
            final String id,
            final String name,
            final String description,
            final boolean active,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.categories = new HashSet<>();
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static PostDataEntity from(final Post aPost) {
        final var anEntity = new PostDataEntity(
                aPost.getId().getValue(),
                aPost.getName(),
                aPost.getDescription(),
                aPost.isActive(),
                aPost.getCreatedAt(),
                aPost.getUpdatedAt(),
                aPost.getDeletedAt()
        );
        aPost.getCategories().forEach(anEntity::addCategory);
        return anEntity;
    }

    public Post toAggregate() {
        return Post.with(
                PostID.from(getId()),
                getName(),
                getDescription(),
                getCategories().stream().map(it -> CategoryID.from(it.getId().getCategoryId())).toList(),
                getActive(),
                getCreatedAt(),
                getUpdatedAt(),
                getDeletedAt()
        );
    }

    private void addCategory(final CategoryID anId) {
        this.categories.add(PostCategoryEntity.from(this, anId));
    }

    private void removeCategory(final CategoryID anId) {
        this.categories.remove(PostCategoryEntity.from(this, anId));
    }

    public Set<PostCategoryEntity> getCategories() {
        return categories;
    }

    public void setCategories(final Set<PostCategoryEntity> categories) {
        this.categories = categories;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }
}

