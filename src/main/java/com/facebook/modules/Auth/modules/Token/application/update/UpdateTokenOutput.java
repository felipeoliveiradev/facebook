package com.facebook.modules.Auth.modules.Token.application.update;


import com.facebook.modules.Auth.modules.Token.domain.Token;

public record UpdateTokenOutput(
        String accessToken,
        String expiresIn,
        String refreshExpiresIn,
        String refreshToken,
        String tokenType,
        String notBeforePolicy,
        String scope
) {

    public static UpdateTokenOutput from(
            final String accessToken,
            final String expiresIn,
            final String refreshExpiresIn,
            final String refreshToken,
            final String tokenType,
            final String notBeforePolicy,
            final String scope
    ) {
        return new UpdateTokenOutput(
                accessToken,
                expiresIn,
                refreshExpiresIn,
                refreshToken,
                tokenType,
                notBeforePolicy,
                scope
        );
    }

    public static UpdateTokenOutput from(final Token aToken) {
        return new UpdateTokenOutput(
                aToken.getAccessToken(),
                aToken.getExpiresIn(),
                aToken.getRefreshExpiresIn(),
                aToken.getRefreshToken(),
                aToken.getTokenType(),
                aToken.getNotBeforePolicy(),
                aToken.getScope()
        );
    }
}
