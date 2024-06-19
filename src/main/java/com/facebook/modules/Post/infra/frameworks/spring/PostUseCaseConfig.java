package com.facebook.modules.Post.infra.frameworks.spring;

import com.facebook.modules.Category.domain.CategoryGateway;
import com.facebook.modules.Post.application.create.CreatePostUseCase;
import com.facebook.modules.Post.application.create.DefaultCreatePostUseCase;
import com.facebook.modules.Post.application.delete.DefaultDeletePostUseCase;
import com.facebook.modules.Post.application.delete.DeletePostUseCase;
import com.facebook.modules.Post.application.retrieve.byId.DefaultPostByIdUseCase;
import com.facebook.modules.Post.application.retrieve.byId.PostByIdUseCase;
import com.facebook.modules.Post.application.retrieve.list.DefaultListPostsUseCase;
import com.facebook.modules.Post.application.retrieve.list.ListPostsUseCase;
import com.facebook.modules.Post.application.update.DefaultUpdatePostUseCase;
import com.facebook.modules.Post.application.update.UpdatePostUseCase;
import com.facebook.modules.Post.domain.PostGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class PostUseCaseConfig {
    private final CategoryGateway categoryGateway;
    private final PostGateway postGateway;

    public PostUseCaseConfig(final CategoryGateway categoryGateway, final PostGateway postGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
        this.postGateway = Objects.requireNonNull(postGateway);
    }

    @Bean
    public CreatePostUseCase createPostUseCase() {
        return new DefaultCreatePostUseCase(categoryGateway, postGateway);
    }

    @Bean
    public DeletePostUseCase deletePostUseCase() {
        return new DefaultDeletePostUseCase(postGateway);
    }

    @Bean
    public PostByIdUseCase PostByIdUseCase() {
        return new DefaultPostByIdUseCase(postGateway, categoryGateway);
    }

    @Bean
    public ListPostsUseCase listPostsUseCase() {
        return new DefaultListPostsUseCase(categoryGateway, postGateway);
    }

    @Bean
    public UpdatePostUseCase updatePostUseCase() {
        return new DefaultUpdatePostUseCase(categoryGateway, postGateway);
    }
}
