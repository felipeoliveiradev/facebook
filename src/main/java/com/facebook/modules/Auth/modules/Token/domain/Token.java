package com.facebook.modules.Auth.modules.Token.domain;


import com.facebook.modules.Flup.system.validation.handlers.ValidationHandler;

public class Token implements Cloneable {
    private String accessToken;
    private String expiresIn;
    private String refreshExpiresIn;
    private String refreshToken;
    private String tokenType;
    private String notBeforePolicy;
    private String scope;

    private Token(
            final String accessTokenField,
            final String expiresInField,
            final String refreshExpiresInField,
            final String refreshTokenField,
            final String tokenTypeField,
            final String notBeforePolicyField,
            final String scopeField
    ) {
        this.accessToken = accessTokenField;
        this.expiresIn = expiresInField;
        this.refreshExpiresIn = refreshExpiresInField;
        this.refreshToken = refreshTokenField;
        this.tokenType = tokenTypeField;
        this.notBeforePolicy = notBeforePolicyField;
        this.scope = scopeField;
    }


    public static Token newToken(
            final String accessTokenField,
            final String expiresInField,
            final String refreshExpiresInField,
            final String refreshTokenField,
            final String tokenTypeField,
            final String notBeforePolicyField,
            final String scopeField
    ) {
        return new Token(
                accessTokenField,
                expiresInField,
                refreshExpiresInField,
                refreshTokenField,
                tokenTypeField,
                notBeforePolicyField,
                scopeField
        );
    }

    public static Token with(
            final Token aToken
    ) {
        return with(
                aToken.accessToken,
                aToken.expiresIn,
                aToken.refreshExpiresIn,
                aToken.refreshToken,
                aToken.tokenType,
                aToken.notBeforePolicy,
                aToken.scope
        );
    }

    public static Token with(
            final String accessToken,
            final String expiresIn,
            final String refreshExpiresIn,
            final String refreshToken,
            final String tokenType,
            final String notBeforePolicy,
            final String scope
    ) {
        return new Token(
                accessToken,
                expiresIn,
                refreshExpiresIn,
                refreshToken,
                tokenType,
                notBeforePolicy,
                scope

        );
    }

    public void validate(final ValidationHandler handler) {
        new TokenValidator(this, handler).validate();
    }

    public Token update(
            final String accessTokenField,
            final String expiresInField,
            final String refreshExpiresInField,
            final String refreshTokenField,
            final String tokenTypeField,
            final String notBeforePolicyField,
            final String scopeField
    ) {
        return new Token(
                accessTokenField,
                expiresInField,
                refreshExpiresInField,
                refreshTokenField,
                tokenTypeField,
                notBeforePolicyField,
                scopeField
        );
    }


    public String getAccessToken() {
        return accessToken;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public String getRefreshExpiresIn() {
        return refreshExpiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getNotBeforePolicy() {
        return notBeforePolicy;
    }

    public String getScope() {
        return scope;
    }


}
