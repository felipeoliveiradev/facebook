package com.facebook.modules.Auth.modules.Token.application.create;

import com.facebook.modules.Flup.system.required.helpers.UseCase;
import com.facebook.modules.Flup.system.validation.handlers.Notification;
import io.vavr.control.Either;

public abstract class CreateTokenUseCase extends UseCase<CreateTokenCommand, Either<Notification, CreateTokenOutput>> {

}
