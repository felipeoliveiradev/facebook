package com.facebook.modules.Post.infra;

import com.facebook.modules.Category.domain.CategoryID;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "posts_categories")
public class PostCategoryEntity {

    @EmbeddedId
    private PostCategoryID id;

    @ManyToOne
    @MapsId("postId")
    private PostDataEntity post;

    public PostCategoryEntity() {
    }

    private PostCategoryEntity(final PostDataEntity aPost, final CategoryID aCategoryID) {
        this.id = PostCategoryID.from(aPost.getId(), aCategoryID.getValue());
        this.post = aPost;
    }

    public static PostCategoryEntity from(final PostDataEntity aPost, final CategoryID aCategoryID) {
        return new PostCategoryEntity(aPost, aCategoryID);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final PostCategoryEntity that = (PostCategoryEntity) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public PostCategoryID getId() {
        return id;
    }

    public void setId(PostCategoryID id) {
        this.id = id;
    }

    public PostDataEntity getPost() {
        return post;
    }

    public void setPost(PostDataEntity post) {
        this.post = post;
    }
}
