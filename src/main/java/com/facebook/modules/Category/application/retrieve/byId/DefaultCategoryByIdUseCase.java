package com.facebook.modules.Category.application.retrieve.byId;


import com.facebook.modules.Category.domain.Category;
import com.facebook.modules.Category.domain.CategoryGateway;
import com.facebook.modules.Category.domain.CategoryID;
import com.facebook.modules.Flup.system.validation.Exceptions.NotFoundException;

import java.util.Objects;
import java.util.function.Supplier;

public class DefaultCategoryByIdUseCase extends CategoryByIdUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultCategoryByIdUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    private static Supplier<NotFoundException> NotFound(final CategoryID anId) {
        return () -> NotFoundException.with(Category.class, anId);
    }

    @Override
    public CategoryOutput execute(final String anIn) {
        final var aCategoryId = CategoryID.from(anIn);
        return this.categoryGateway.findById(aCategoryId)
                .map(CategoryOutput::from)
                .orElseThrow(NotFound(aCategoryId));
    }
}
