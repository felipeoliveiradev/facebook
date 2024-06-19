package com.facebook.modules.Auth.modules.Token.application.create;

public record CreateTokenCommand(
        String username,
        String password
) {
    public static CreateTokenCommand with(
            final String username,
            final String password
    ) {
        return new CreateTokenCommand(
                username,
                password
        );
    }
}
