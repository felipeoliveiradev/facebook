package com.facebook.modules.Post.infra.frameworks.spring;


import com.facebook.modules.Flup.system.required.pagination.Pagination;
import com.facebook.modules.Flup.system.required.pagination.SearchQuery;
import com.facebook.modules.Flup.system.validation.handlers.Notification;
import com.facebook.modules.Post.application.create.CreatePostCommand;
import com.facebook.modules.Post.application.create.CreatePostOutput;
import com.facebook.modules.Post.application.create.CreatePostUseCase;
import com.facebook.modules.Post.application.delete.DeletePostUseCase;
import com.facebook.modules.Post.application.retrieve.byId.PostByIdUseCase;
import com.facebook.modules.Post.application.retrieve.list.ListPostsUseCase;
import com.facebook.modules.Post.application.update.UpdatePostCommand;
import com.facebook.modules.Post.application.update.UpdatePostOutput;
import com.facebook.modules.Post.application.update.UpdatePostUseCase;
import com.facebook.modules.Post.infra.PostAPIPresenter;
import com.facebook.modules.Post.infra.http.request.CreatePostRequest;
import com.facebook.modules.Post.infra.http.request.UpdatePostRequest;
import com.facebook.modules.Post.infra.http.response.ListPostsResponse;
import com.facebook.modules.Post.infra.http.response.PostResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;
import java.util.function.Function;

@RestController
public class PostController implements PostAPI {

    private final CreatePostUseCase postUseCase;
    private final PostByIdUseCase postByIdUseCase;
    private final UpdatePostUseCase updatePostUseCase;
    private final DeletePostUseCase deletePostUseCase;
    private final ListPostsUseCase listPostsUseCase;

    public PostController(
            final CreatePostUseCase postUseCase,
            final PostByIdUseCase postByIdUseCase,
            final UpdatePostUseCase updatePostUseCase,
            final DeletePostUseCase deletePostUseCase,
            final ListPostsUseCase listPostsUseCase
    ) {
        this.postUseCase = Objects.requireNonNull(postUseCase);
        this.postByIdUseCase = Objects.requireNonNull(postByIdUseCase);
        this.updatePostUseCase = updatePostUseCase;
        this.deletePostUseCase = deletePostUseCase;
        this.listPostsUseCase = listPostsUseCase;
    }

    @Override
    public ResponseEntity<?> createPost(final CreatePostRequest input) {

        final CreatePostCommand aCommand = CreatePostCommand.with(
                input.name(),
                input.description(),
                input.categories(),
                input.active() != null ? input.active() : true
        );

        final Function<Notification, ResponseEntity> onError = ResponseEntity.unprocessableEntity()::body;
        final Function<CreatePostOutput, ResponseEntity> onSuccess = output -> ResponseEntity.created(URI.create("/Posts/" + output.id())).body(output);
        return this.postUseCase.execute(aCommand).fold(onError, onSuccess);
    }

    @Override
    public Pagination<ListPostsResponse> listPosts(
            final String search,
            int page,
            int perPage,
            final String sort,
            final String direction
    ) {
        return listPostsUseCase.execute(new SearchQuery(page, perPage, search, sort, direction)).map(PostAPIPresenter::present);
    }

    @Override
    public PostResponse getById(final String id) {
        return PostAPIPresenter.present(this.postByIdUseCase.execute(id));
    }

    @Override
    public ResponseEntity<?> updateById(final String id, final UpdatePostRequest input) {
        final UpdatePostCommand aCommand = UpdatePostCommand.with(
                id,
                input.name(),
                input.description(),
                input.categories(),
                input.active() != null ? input.active() : true
        );

        final Function<Notification, ResponseEntity<?>> onError = ResponseEntity.unprocessableEntity()::body;
        final Function<UpdatePostOutput, ResponseEntity<?>> onSuccess = ResponseEntity::ok;
        return this.updatePostUseCase.execute(aCommand).fold(onError, onSuccess);
    }

    @Override
    public void deleteById(final String id) {
        this.deletePostUseCase.execute(id);
    }
}
