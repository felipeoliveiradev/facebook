package com.facebook.modules.Category.application.update;

import com.facebook.modules.Category.domain.Category;
import com.facebook.modules.Category.domain.CategoryGateway;
import com.facebook.modules.Category.domain.CategoryID;
import com.facebook.modules.Flup.system.validation.Exceptions.DomainException;
import com.facebook.modules.Flup.system.validation.Exceptions.NotFoundException;
import com.facebook.modules.Flup.system.validation.handlers.Notification;
import io.vavr.control.Either;

import java.util.Objects;
import java.util.function.Supplier;

import static io.vavr.API.Left;
import static io.vavr.API.Try;

public class DefaultUpdateCategoryUseCase extends UpdateCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultUpdateCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    private static Supplier<DomainException> NotFound(final CategoryID anId) {
        return () -> NotFoundException.with(Category.class, CategoryID.from(anId.getValue()));
    }

    @Override
    public Either<Notification, UpdateCategoryOutput> execute(final UpdateCategoryCommand aCommand) {
        final CategoryID anId = CategoryID.from(aCommand.id());
        final String nameField = aCommand.name();
        final boolean isActive = aCommand.isActive();

        final Category aCategory = this.categoryGateway.findById(anId)
                .orElseThrow(NotFound(anId));

        final Notification notificationHandler = Notification.create();
        aCategory.update(
                nameField,
                isActive
        ).validate(notificationHandler);

        return notificationHandler.hasError() ? Left(notificationHandler) : update(aCategory);
    }

    private Either<Notification, UpdateCategoryOutput> update(final Category aCategory) {
        return Try(() -> this.categoryGateway.update(aCategory))
                .toEither()
                .bimap(Notification::create, UpdateCategoryOutput::from);
    }
}
