package com.facebook.modules.Auth.modules.Token.application.create;

import com.facebook.modules.Auth.modules.Token.domain.TokenGateway;
import com.facebook.modules.Flup.system.validation.handlers.Notification;
import io.vavr.control.Either;

import java.util.Objects;

import static io.vavr.API.Try;

public class DefaultCreateTokenUseCase extends CreateTokenUseCase {

    private final TokenGateway tokenGateway;

    public DefaultCreateTokenUseCase(final TokenGateway tokenGateway) {
        this.tokenGateway = Objects.requireNonNull(tokenGateway);
    }

    @Override
    public Either<Notification, CreateTokenOutput> execute(final CreateTokenCommand aCommand) {
        final String username = aCommand.username();
        final String password = aCommand.password();
        return create(username, password);
    }

    private Either<Notification, CreateTokenOutput> create(final String username, final String password) {
        return Try(() -> this.tokenGateway.create(username, password))
                .toEither()
                .bimap(Notification::create, CreateTokenOutput::create);
    }
}
