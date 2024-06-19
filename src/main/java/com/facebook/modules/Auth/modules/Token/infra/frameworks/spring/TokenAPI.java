package com.facebook.modules.Auth.modules.Token.infra.frameworks.spring;


import com.facebook.modules.Auth.modules.Token.domain.Token;
import com.facebook.modules.Auth.modules.Token.infra.http.request.CreateTokenRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping(value = "tokens")
@Tag(name = "Tokens")
public interface TokenAPI {
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create a new Token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created successfully", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", description = "Not Found a Token", useReturnTypeSchema = false),
            @ApiResponse(responseCode = "422", description = "Invalid params", useReturnTypeSchema = false),
            @ApiResponse(responseCode = "500", description = "An Internal Server Error", useReturnTypeSchema = false)
    })
    ResponseEntity<Token> createToken(@RequestBody @Valid CreateTokenRequest input);


    @PutMapping(
            value = "{token}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Update a Token by it`s identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token updated successfully"),
            @ApiResponse(responseCode = "404", description = "Not Found a Token"),
            @ApiResponse(responseCode = "500", description = "An Internal Server Error")
    })
    ResponseEntity<?> updateByToken(@PathVariable(name = "token") String token);

}
