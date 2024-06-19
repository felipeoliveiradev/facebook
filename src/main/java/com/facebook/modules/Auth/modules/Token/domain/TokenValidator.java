package com.facebook.modules.Auth.modules.Token.domain;

import com.facebook.modules.Flup.system.validation.Error;
import com.facebook.modules.Flup.system.validation.ValidationField;
import com.facebook.modules.Flup.system.validation.Validator;
import com.facebook.modules.Flup.system.validation.handlers.ValidationHandler;

public class TokenValidator extends Validator {
    private final Token Token;

    public TokenValidator(final Token aToken, final ValidationHandler aHandler) {
        super(aHandler);
        this.Token = aToken;
    }

    @Override
    public void validate() {
        this.accessTokenValidation();
        this.expiresInValidation();
        this.refreshExpiresInValidation();
        this.refreshTokenValidation();
        this.tokenTypeValidation();
        this.notBeforePolicyValidation();
        this.scopeValidation();
    }


    public void accessTokenValidation() {
        try {
            this.validationHandler().appendList(new ValidationField("accessToken", this.Token.getAccessToken()).Null().Min(3).validate());
        } catch (Exception Err) {
            this.validationHandler().append(new Error(Err.getMessage()));
        }
    }


    public void expiresInValidation() {
        try {
            this.validationHandler().appendList(new ValidationField("expiresIn", this.Token.getExpiresIn()).Null().Min(3).validate());
        } catch (Exception Err) {
            this.validationHandler().append(new Error(Err.getMessage()));
        }
    }


    public void refreshExpiresInValidation() {
        try {
            this.validationHandler().appendList(new ValidationField("refreshExpiresIn", this.Token.getRefreshExpiresIn()).Null().Min(3).validate());
        } catch (Exception Err) {
            this.validationHandler().append(new Error(Err.getMessage()));
        }
    }


    public void refreshTokenValidation() {
        try {
            this.validationHandler().appendList(new ValidationField("refreshToken", this.Token.getRefreshToken()).Null().Min(3).validate());
        } catch (Exception Err) {
            this.validationHandler().append(new Error(Err.getMessage()));
        }
    }


    public void tokenTypeValidation() {
        try {
            this.validationHandler().appendList(new ValidationField("tokenType", this.Token.getTokenType()).Null().Min(3).validate());
        } catch (Exception Err) {
            this.validationHandler().append(new Error(Err.getMessage()));
        }
    }


    public void notBeforePolicyValidation() {
        try {
            this.validationHandler().appendList(new ValidationField("notBeforePolicy", this.Token.getNotBeforePolicy()).Null().Min(1).validate());
        } catch (Exception Err) {
            this.validationHandler().append(new Error(Err.getMessage()));
        }
    }


    public void scopeValidation() {
        try {
            this.validationHandler().appendList(new ValidationField("scope", this.Token.getScope()).Null().Min(3).validate());
        } catch (Exception Err) {
            this.validationHandler().append(new Error(Err.getMessage()));
        }
    }

}
