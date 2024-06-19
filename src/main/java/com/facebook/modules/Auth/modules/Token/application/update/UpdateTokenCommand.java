package com.facebook.modules.Auth.modules.Token.application.update;

public record UpdateTokenCommand(
        String refreshToken
) {
    public static UpdateTokenCommand with(
            final String refreshTokenField
    ) {
        return new UpdateTokenCommand(
                refreshTokenField
        );
    }
}
