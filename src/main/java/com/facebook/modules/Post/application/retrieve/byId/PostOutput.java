package com.facebook.modules.Post.application.retrieve.byId;


import com.facebook.modules.Post.domain.Post;

import java.time.Instant;
import java.util.ArrayList;

public record PostOutput(
        String id,
        String name,
        String description,
        ArrayList<Object[]> categories,
        boolean isActive,
        Instant createdAt,
        Instant updatedAt,
        Instant deletedAt
) {
    public static PostOutput from(final Post aPost, final ArrayList<Object[]> aCategories) {
        return new PostOutput(
                aPost.getId().getValue(),
                aPost.getName(),
                aPost.getDescription(),
                aCategories,
                aPost.isActive(),
                aPost.getCreatedAt(),
                aPost.getUpdatedAt(),
                aPost.getDeletedAt()
        );
    }
}
