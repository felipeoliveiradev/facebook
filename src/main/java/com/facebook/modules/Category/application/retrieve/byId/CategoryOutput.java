package com.facebook.modules.Category.application.retrieve.byId;


import com.facebook.modules.Category.domain.Category;

import java.time.Instant;

public record CategoryOutput(
        String id,
        String name,
        boolean isActive,
        Instant createdAt,
        Instant updatedAt,
        Instant deletedAt
) {
    public static CategoryOutput from(final Category aCategory) {
        return new CategoryOutput(
                aCategory.getId().getValue(),
                aCategory.getName(),
                aCategory.isActive(),
                aCategory.getCreatedAt(),
                aCategory.getUpdatedAt(),
                aCategory.getDeletedAt()
        );
    }
}
