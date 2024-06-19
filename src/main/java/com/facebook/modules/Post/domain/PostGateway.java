package com.facebook.modules.Post.domain;

import com.facebook.modules.Flup.system.required.pagination.Pagination;
import com.facebook.modules.Flup.system.required.pagination.SearchQuery;

import java.util.Optional;

public interface PostGateway {
    Post create(Post aPost);

    void deleteById(PostID anID);

    Optional<Post> findById(PostID anID);

    Post update(Post aPost);

    Pagination<Post> findAll(SearchQuery aQuery);

}
