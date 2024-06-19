package com.facebook.modules.Post.application.delete;

import com.facebook.modules.Post.domain.Post;
import com.facebook.modules.Post.domain.PostID;

public record DeletePostOutput(
        PostID id
) {
    public static DeletePostOutput from(final Post aPost) {
        return new DeletePostOutput(
                aPost.getId()
        );
    }
}
