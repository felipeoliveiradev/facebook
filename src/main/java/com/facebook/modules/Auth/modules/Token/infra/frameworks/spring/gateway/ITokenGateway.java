package com.facebook.modules.Auth.modules.Token.infra.frameworks.spring.gateway;

import com.facebook.modules.Auth.modules.Token.infra.http.response.TokenResponse;


public interface ITokenGateway {
    TokenResponse create(String username, String password);

    TokenResponse update(String token);
}
