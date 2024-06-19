package com.facebook.modules.Category.application.retrieve.list;


import com.facebook.modules.Category.domain.Category;
import com.facebook.modules.Category.domain.CategoryID;

import java.time.Instant;

public record ListCategoriesOutput(
        CategoryID id,
        String name,
        boolean isActive,
        Instant createdAt,
        Instant updatedAt,
        Instant deletedAt
) {
    public static ListCategoriesOutput from(final Category aCategory) {
        return new ListCategoriesOutput(
                aCategory.getId(),
                aCategory.getName(),
                aCategory.isActive(),
                aCategory.getUpdatedAt(),
                aCategory.getCreatedAt(),
                aCategory.getDeletedAt()
        );
    }
}
