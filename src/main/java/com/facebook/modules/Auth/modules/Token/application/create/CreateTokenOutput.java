package com.facebook.modules.Auth.modules.Token.application.create;

import com.facebook.modules.Auth.modules.Token.domain.Token;


public record CreateTokenOutput(
        String accessToken,
        String expiresIn,
        String refreshExpiresIn,
        String refreshToken,
        String tokenType,
        String notBeforePolicy,
        String scope
) {
    public static CreateTokenOutput from(
            String accessToken,
            String expiresIn,
            String refreshExpiresIn,
            String refreshToken,
            String tokenType,
            String notBeforePolicy,
            String scope
    ) {
        return new CreateTokenOutput(
                accessToken,
                expiresIn,
                refreshExpiresIn,
                refreshToken,
                tokenType,
                notBeforePolicy,
                scope
        );
    }

    public static CreateTokenOutput from(final Token aToken) {
        return new CreateTokenOutput(
                aToken.getAccessToken(),
                aToken.getExpiresIn(),
                aToken.getRefreshExpiresIn(),
                aToken.getRefreshToken(),
                aToken.getTokenType(),
                aToken.getNotBeforePolicy(),
                aToken.getScope()
        );
    }

    public static CreateTokenOutput create(final Token aToken) {
        return new CreateTokenOutput(
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
