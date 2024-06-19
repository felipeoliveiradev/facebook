package com.facebook.modules.Post.application.update;

import com.facebook.modules.Category.domain.CategoryGateway;
import com.facebook.modules.Category.domain.CategoryID;
import com.facebook.modules.Flup.system.validation.Error;
import com.facebook.modules.Flup.system.validation.Exceptions.DomainException;
import com.facebook.modules.Flup.system.validation.Exceptions.NotFoundException;
import com.facebook.modules.Flup.system.validation.handlers.Notification;
import com.facebook.modules.Flup.system.validation.handlers.ValidationHandler;
import com.facebook.modules.Post.domain.Post;
import com.facebook.modules.Post.domain.PostGateway;
import com.facebook.modules.Post.domain.PostID;
import io.vavr.control.Either;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static io.vavr.API.Left;
import static io.vavr.API.Try;

public class DefaultUpdatePostUseCase extends UpdatePostUseCase {

    private final CategoryGateway categoryGateway;
    private final PostGateway postGateway;

    public DefaultUpdatePostUseCase(final CategoryGateway categoryGateway, final PostGateway postGateway) {
        this.postGateway = Objects.requireNonNull(postGateway);
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    private static Supplier<DomainException> NotFound(final PostID anId) {
        return () -> NotFoundException.with(Post.class, PostID.from(anId.getValue()));
    }

    @Override
    public Either<Notification, UpdatePostOutput> execute(final UpdatePostCommand aCommand) {
        final PostID anId = PostID.from(aCommand.id());
        final String nameField = aCommand.name();
        final String descriptionField = aCommand.description();
        final List<CategoryID> categoriesField = toCategoryId(aCommand.categories());
        final boolean isActive = aCommand.isActive();

        final Post aPost = this.postGateway.findById(anId)
                .orElseThrow(NotFound(anId));

        final Notification notificationHandler = Notification.create();
        notificationHandler.append(validateCategories(categoriesField));
        notificationHandler.validate(() -> aPost.update(nameField, descriptionField, categoriesField, isActive));
        aPost.validate(notificationHandler);
        aPost.update(
                nameField,
                descriptionField,
                categoriesField,
                isActive
        ).validate(notificationHandler);

        return notificationHandler.hasError() ? Left(notificationHandler) : update(aPost);
    }

    private ValidationHandler validateCategories(final List<CategoryID> ids) {
        final Notification notificationHandler = Notification.create();
        if (ids == null || ids.isEmpty()) {
            return notificationHandler;
        }
        final var retrieveIds = categoryGateway.existsByIds(ids);

        if (ids.size() != retrieveIds.size()) {
            final ArrayList<CategoryID> missingIds = new ArrayList<>(ids);
            missingIds.removeAll(retrieveIds);
            final var missingIdsMessage = missingIds.stream().map(CategoryID::getValue).collect(Collectors.joining(", "));
            notificationHandler.append(new Error("Some categories could not be found: %s".formatted(missingIdsMessage)));
        }

        return notificationHandler;
    }

    private List<CategoryID> toCategoryId(final List<String> categories) {
        return categories.stream().map(CategoryID::from).toList();
    }

    private Either<Notification, UpdatePostOutput> update(final Post aPost) {
        return Try(() -> this.postGateway.update(aPost))
                .toEither()
                .bimap(Notification::create, UpdatePostOutput::from);
    }
}
