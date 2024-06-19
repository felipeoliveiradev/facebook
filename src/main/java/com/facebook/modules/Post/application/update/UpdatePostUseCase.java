package com.facebook.modules.Post.application.update;

import com.facebook.modules.Flup.system.required.helpers.UseCase;
import com.facebook.modules.Flup.system.validation.handlers.Notification;
import io.vavr.control.Either;

public abstract class UpdatePostUseCase extends UseCase<UpdatePostCommand, Either<Notification, UpdatePostOutput>> {

}
