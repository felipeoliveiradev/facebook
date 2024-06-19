package com.facebook.modules.Post.domain;

public record PostSearchQuery(
        int page,
        int perPage,
        String terms,
        String sort,
        String direction
) {
}
