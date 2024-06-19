package com.facebook.modules.Post.application.retrieve.byId;


import com.facebook.modules.Category.application.retrieve.byId.CategoryOutput;
import com.facebook.modules.Category.domain.CategoryGateway;
import com.facebook.modules.Category.domain.CategoryID;
import com.facebook.modules.Category.infra.CategoryAPIPresenter;
import com.facebook.modules.Flup.system.validation.Exceptions.NotFoundException;
import com.facebook.modules.Post.domain.Post;
import com.facebook.modules.Post.domain.PostGateway;
import com.facebook.modules.Post.domain.PostID;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Supplier;

public class DefaultPostByIdUseCase extends PostByIdUseCase {

    private final PostGateway postGateway;
    private final CategoryGateway categoryGateway;

    public DefaultPostByIdUseCase(final PostGateway postGateway, final CategoryGateway categoryGateway) {
        this.postGateway = Objects.requireNonNull(postGateway);
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    private static Supplier<NotFoundException> NotFound(final PostID anId) {
        return () -> NotFoundException.with(Post.class, anId);
    }

    @Override
    public PostOutput execute(final String anIn) {
        final var aPostId = PostID.from(anIn);
        return this.postGateway.findById(aPostId)
                .map(it -> {
                    ArrayList<Object[]> teste = new ArrayList<>();
                    it.getCategories().stream().map(it2 -> teste.add(this.categoryGateway.findById(CategoryID.from(it2.getValue())).stream().map(it3 -> CategoryAPIPresenter.present(CategoryOutput.from(it3))).toArray())).toList();
                    return new PostOutput(it.getId().getValue(), it.getName(), it.getDescription(), teste, it.isActive(), it.getCreatedAt(), it.getUpdatedAt(), it.getDeletedAt());
                })
                .orElseThrow(NotFound(aPostId));
    }
}
