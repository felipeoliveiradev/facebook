package com.facebook.modules.Post.application.create;

import com.facebook.modules.Category.domain.CategoryGateway;
import com.facebook.modules.Category.domain.CategoryID;
import com.facebook.modules.Flup.system.validation.Error;
import com.facebook.modules.Flup.system.validation.handlers.Notification;
import com.facebook.modules.Flup.system.validation.handlers.ValidationHandler;
import com.facebook.modules.Post.domain.Post;
import com.facebook.modules.Post.domain.PostGateway;
import io.vavr.control.Either;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static io.vavr.API.Left;
import static io.vavr.API.Try;

public class DefaultCreatePostUseCase extends CreatePostUseCase {

    private final CategoryGateway categoryGateway;
    private final PostGateway postGateway;

    public DefaultCreatePostUseCase(final CategoryGateway categoryGateway, final PostGateway postGateway) {
        this.postGateway = Objects.requireNonNull(postGateway);
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Either<Notification, CreatePostOutput> execute(final CreatePostCommand aCommand) {
        final String nameField = aCommand.name();
        final String descriptionField = aCommand.description();
        final List<CategoryID> categories = toCategoryID(aCommand.categories());
        final boolean isActive = aCommand.isActive();

        final var notificationHandler = Notification.create();
        notificationHandler.append(validateCategories(categories));
        final Post aPost = notificationHandler.validate(() -> Post.newPost(nameField, descriptionField, categories, isActive));
        aPost.validate(notificationHandler);

        return notificationHandler.hasError() ? Left(notificationHandler) : create(aPost);
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

    private List<CategoryID> toCategoryID(final List<String> categories) {
        return categories.stream().map(CategoryID::from)
                .toList();
    }

    private Either<Notification, CreatePostOutput> create(final Post aPost) {
        return Try(() -> this.postGateway.create(aPost))
                .toEither()
                .bimap(Notification::create, CreatePostOutput::from);
    }
}
