package com.facebook.modules.Auth.modules.Token.application.update;

import com.facebook.modules.Auth.modules.Token.domain.TokenGateway;
import com.facebook.modules.Flup.system.validation.handlers.Notification;
import io.vavr.control.Either;

import java.util.Objects;

import static io.vavr.API.Try;

public class DefaultUpdateTokenUseCase extends UpdateTokenUseCase {

    private final TokenGateway tokenGateway;

    public DefaultUpdateTokenUseCase(final TokenGateway tokenGateway) {
        this.tokenGateway = Objects.requireNonNull(tokenGateway);
    }

    @Override
    public Either<Notification, UpdateTokenOutput> execute(final UpdateTokenCommand aCommand) {
        final String refreshTokenField = aCommand.refreshToken();
        return update(refreshTokenField);
    }

    private Either<Notification, UpdateTokenOutput> update(final String aToken) {
        return Try(() -> this.tokenGateway.update(aToken))
                .toEither()
                .bimap(Notification::create, UpdateTokenOutput::from);
    }
}
