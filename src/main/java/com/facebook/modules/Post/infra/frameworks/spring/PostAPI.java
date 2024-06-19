package com.facebook.modules.Post.infra.frameworks.spring;


import com.facebook.modules.Flup.system.required.pagination.Pagination;
import com.facebook.modules.Post.infra.http.request.CreatePostRequest;
import com.facebook.modules.Post.infra.http.request.UpdatePostRequest;
import com.facebook.modules.Post.infra.http.response.ListPostsResponse;
import com.facebook.modules.Post.infra.http.response.PostResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping(value = "posts")
@Tag(name = "posts")
public interface PostAPI {
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create a new Post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created successfully", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", description = "Not Found a Post"),
            @ApiResponse(responseCode = "422", description = "Invalid params"),
            @ApiResponse(responseCode = "500", description = "An Internal Server Error")
    })
    ResponseEntity<?> createPost(@RequestBody @Valid CreatePostRequest input);

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "List all Posts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listed successfully"),
            @ApiResponse(responseCode = "404", description = "Not Found a Post"),
            @ApiResponse(responseCode = "422", description = "Invalid params"),
            @ApiResponse(responseCode = "500", description = "An Internal Server Error")
    })
    Pagination<ListPostsResponse> listPosts(
            @RequestParam(name = "search", required = false, defaultValue = "") final String search,
            @RequestParam(name = "page", required = false, defaultValue = "0") final int page,
            @RequestParam(name = "perPage", required = false, defaultValue = "10") final int perPage,
            @RequestParam(name = "sort", required = false, defaultValue = "name") final String sort,
            @RequestParam(name = "direction", required = false, defaultValue = "asc") final String direction
    );

    @GetMapping(
            value = "{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Get a Post by it`s identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Not Found a Post"),
            @ApiResponse(responseCode = "500", description = "An Internal Server Error")
    })
    PostResponse getById(@PathVariable(name = "id") String id);


    @PutMapping(
            value = "{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Update a Post by it`s identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post updated successfully"),
            @ApiResponse(responseCode = "404", description = "Not Found a Post"),
            @ApiResponse(responseCode = "500", description = "An Internal Server Error")
    })
    ResponseEntity<?> updateById(@PathVariable(name = "id") String id, @RequestBody UpdatePostRequest input);


    @DeleteMapping(
            value = "{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a Post by it`s identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Post deleted successfully"),
            @ApiResponse(responseCode = "500", description = "An Internal Server Error")
    })
    void deleteById(@PathVariable(name = "id") String id);


}
