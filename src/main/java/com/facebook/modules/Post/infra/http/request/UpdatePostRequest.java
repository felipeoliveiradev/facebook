package com.facebook.modules.Post.infra.http.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

public record UpdatePostRequest(
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("categories_id") List<String> categories,
        @JsonProperty("is_active") Boolean active
) {
    public List<String> categories() {
        return this.categories != null ? this.categories : Collections.emptyList();
    }
}
