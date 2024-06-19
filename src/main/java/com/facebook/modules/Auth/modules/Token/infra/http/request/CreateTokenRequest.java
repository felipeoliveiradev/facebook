package com.facebook.modules.Auth.modules.Token.infra.http.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateTokenRequest(
        @JsonProperty("username") String username,
        @JsonProperty("password") String password
) {
}
