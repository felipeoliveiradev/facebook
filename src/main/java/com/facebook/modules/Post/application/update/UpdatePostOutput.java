package com.facebook.modules.Post.application.update;


import com.facebook.modules.Post.domain.Post;

public record UpdatePostOutput(
        String id
) {

    public static UpdatePostOutput from(final String anId) {
        return new UpdatePostOutput(anId);
    }

    public static UpdatePostOutput from(final Post aPost) {
        return new UpdatePostOutput(aPost.getId().getValue());
    }
}
