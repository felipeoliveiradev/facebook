package com.facebook.modules.Category.infra.http.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateCategoryRequest(
        @JsonProperty("name") String name,
        @JsonProperty("is_active") Boolean active
) {
}
