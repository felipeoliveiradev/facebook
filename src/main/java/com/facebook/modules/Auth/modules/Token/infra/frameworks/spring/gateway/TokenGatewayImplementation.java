package com.facebook.modules.Auth.modules.Token.infra.frameworks.spring.gateway;

import com.facebook.modules.Auth.modules.Token.infra.http.response.TokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class TokenGatewayImplementation implements ITokenGateway {
    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String url;
    @Value("${spring.security.oauth2.resourceserver.jwt.client-id}")
    private String clientId;

    @Override
    public TokenResponse create(String username, String password) {
        LinkedMultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("username", username);
        formData.add("password", password);
        formData.add("grant_type", "password");
        var result = TokenHttp(formData);
        return result.getBody();
    }

    @Override
    public TokenResponse update(String token) {
        LinkedMultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("refresh_token", token);
        formData.add("grant_type", "refresh_token");
        var result = TokenHttp(formData);
        return result.getBody();
    }

    private ResponseEntity<TokenResponse> TokenHttp(LinkedMultiValueMap<String, String> formData) {
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        formData.add("client_id", clientId);
        HttpEntity<LinkedMultiValueMap<String, String>> entity
                = new HttpEntity<LinkedMultiValueMap<String, String>>(formData, headers);
        return restTemplate.postForEntity("%s/protocol/openid-connect/token".formatted(url), entity, TokenResponse.class);
    }
}
