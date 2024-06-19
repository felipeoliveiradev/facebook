package com.facebook.modules.Category.application.delete;

import com.facebook.modules.Category.domain.CategoryGateway;
import com.facebook.modules.Category.domain.CategoryID;
import com.facebook.modules.Flup.system.validation.Error;
import com.facebook.modules.Flup.system.validation.Exceptions.DomainException;

import java.util.Objects;
import java.util.function.Supplier;

public class DefaultDeleteCategoryUseCase extends DeleteCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultDeleteCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    private static Supplier<DomainException> NotFound(final CategoryID anId) {
        return () -> DomainException.with(new Error("Category with ID %s was not found".formatted(anId.getValue())));
    }

    @Override
    public void execute(final String anIn) {
        final var aCategoryId = CategoryID.from(anIn);
        this.categoryGateway.findById(CategoryID.from(anIn)).map(DeleteCategoryOutput::from).orElseThrow(NotFound(aCategoryId));
        this.categoryGateway.deleteById(CategoryID.from(anIn));
    }
}
