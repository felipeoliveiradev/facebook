package com.facebook.modules.Post.infra;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PostCategoryID implements Serializable {

    @Column(name = "post_id", nullable = false)
    private String postId;

    @Column(name = "category_id", nullable = false)
    private String categoryId;

    public PostCategoryID() {
    }

    private PostCategoryID(final String aPostId, final String aCategoryId) {
        this.postId = aPostId;
        this.categoryId = aCategoryId;
    }

    public static PostCategoryID from(final String aPostId, final String aCategoryId) {
        return new PostCategoryID(aPostId, aCategoryId);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final PostCategoryID that = (PostCategoryID) o;
        return Objects.equals(getPostId(), that.getPostId()) && Objects.equals(getCategoryId(), that.getCategoryId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPostId(), getCategoryId());
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
