package com.facebook.modules.Post.application.retrieve.list;


import com.facebook.modules.Post.domain.Post;
import com.facebook.modules.Post.domain.PostID;

import java.time.Instant;
import java.util.ArrayList;

public record ListPostsOutput(
        PostID id,
        String name,
        String description,
        ArrayList<Object[]> categories,
        boolean isActive,
        Instant createdAt,
        Instant updatedAt,
        Instant deletedAt
) {
    public static ListPostsOutput from(final Post aPost, final ArrayList<Object[]> aCategories) {
        return new ListPostsOutput(
                aPost.getId(),
                aPost.getName(),
                aPost.getDescription(),
                aCategories,
                aPost.isActive(),
                aPost.getUpdatedAt(),
                aPost.getCreatedAt(),
                aPost.getDeletedAt()
        );
    }
}
