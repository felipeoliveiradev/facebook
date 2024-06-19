package com.facebook.modules.Auth.modules.Token.infra.frameworks.spring.gateway;

import com.facebook.modules.Auth.modules.Token.domain.Token;
import org.springframework.stereotype.Component;

@Component
public class TokenGateway implements com.facebook.modules.Auth.modules.Token.domain.TokenGateway {
    TokenGatewayImplementation tokenGatewayImplementation;

    public TokenGateway(final TokenGatewayImplementation tokenGatewayImplementation) {
        this.tokenGatewayImplementation = tokenGatewayImplementation;
    }

    @Override
    public Token create(String username, String password) {
        var test = this.tokenGatewayImplementation.create(username, password);

        return Token.newToken(
                test.accessToken(),
                test.expiresIn(),
                test.refreshExpiresIn(),
                test.refreshToken(),
                test.tokenType(),
                test.notBeforePolicy(),
                test.scope()
        );
    }

    @Override
    public Token update(String aToken) {
        var test = this.tokenGatewayImplementation.update(aToken);
        return Token.newToken(
                test.accessToken(),
                test.expiresIn(),
                test.refreshExpiresIn(),
                test.refreshToken(),
                test.tokenType(),
                test.notBeforePolicy(),
                test.scope()
        );
    }
}
