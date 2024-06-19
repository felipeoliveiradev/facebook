package com.facebook.modules.Post.application.create;

import com.facebook.modules.Flup.system.required.helpers.UseCase;
import com.facebook.modules.Flup.system.validation.handlers.Notification;
import io.vavr.control.Either;

public abstract class CreatePostUseCase extends UseCase<CreatePostCommand, Either<Notification, CreatePostOutput>> {

}
