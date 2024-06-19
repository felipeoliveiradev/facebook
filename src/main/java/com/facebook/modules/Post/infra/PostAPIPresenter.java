package com.facebook.modules.Post.infra;

import com.facebook.modules.Post.application.retrieve.byId.PostOutput;
import com.facebook.modules.Post.application.retrieve.list.ListPostsOutput;
import com.facebook.modules.Post.infra.http.response.ListPostsResponse;
import com.facebook.modules.Post.infra.http.response.PostResponse;

public interface PostAPIPresenter {

    static PostResponse present(final PostOutput output) {
        return new PostResponse(
                output.id(),
                output.name(),
                output.description(),
                output.categories(),
                output.isActive(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }

    static ListPostsResponse present(final ListPostsOutput output) {
        return new ListPostsResponse(
                output.id().getValue(),
                output.name(),
                output.description(),
                output.categories(),
                output.isActive(),
                output.updatedAt(),
                output.createdAt(),
                output.deletedAt()
        );
    }
}
