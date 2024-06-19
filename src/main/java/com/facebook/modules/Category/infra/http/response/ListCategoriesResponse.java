package com.facebook.modules.Category.infra.http.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public record ListCategoriesResponse(
        @JsonProperty("id") String id,
        @JsonProperty("name") String name,
        @JsonProperty("is_active") Boolean active,
        @JsonProperty("created_at") Instant createdAt,
        @JsonProperty("updated_at") Instant updatedAt,
        @JsonProperty("deleted_at") Instant deletedAt
) {
}