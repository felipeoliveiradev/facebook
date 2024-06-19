package com.facebook.modules.Post.application.create;

import com.facebook.modules.Post.domain.Post;

public record CreatePostOutput(
        String id
) {
    public static CreatePostOutput from(final String anId) {
        return new CreatePostOutput(anId);
    }

    public static CreatePostOutput from(final Post aPost) {
        return new CreatePostOutput(aPost.getId().getValue());
    }
}
