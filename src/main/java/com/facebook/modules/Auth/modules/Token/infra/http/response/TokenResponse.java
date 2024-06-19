package com.facebook.modules.Auth.modules.Token.infra.http.response;

import com.facebook.modules.Auth.modules.Token.domain.Token;
import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenResponse(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("expires_in") String expiresIn,
        @JsonProperty("refresh_expires_in") String refreshExpiresIn,
        @JsonProperty("refresh_token") String refreshToken,
        @JsonProperty("token_type") String tokenType,
        @JsonProperty("not_before_policy") String notBeforePolicy,
        @JsonProperty("scope") String scope

) {
    public static TokenResponse from(Token aToken) {
        return new TokenResponse(
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
