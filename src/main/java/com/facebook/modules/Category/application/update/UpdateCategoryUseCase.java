package com.facebook.modules.Category.application.update;

import com.facebook.modules.Flup.system.required.helpers.UseCase;
import com.facebook.modules.Flup.system.validation.handlers.Notification;
import io.vavr.control.Either;

public abstract class UpdateCategoryUseCase extends UseCase<UpdateCategoryCommand, Either<Notification, UpdateCategoryOutput>> {

}
