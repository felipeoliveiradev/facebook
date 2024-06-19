package com.facebook.modules.Post.application.delete;

import com.facebook.modules.Flup.system.validation.Error;
import com.facebook.modules.Flup.system.validation.Exceptions.DomainException;
import com.facebook.modules.Post.domain.PostGateway;
import com.facebook.modules.Post.domain.PostID;

import java.util.Objects;
import java.util.function.Supplier;

public class DefaultDeletePostUseCase extends DeletePostUseCase {

    private final PostGateway postGateway;

    public DefaultDeletePostUseCase(final PostGateway postGateway) {
        this.postGateway = Objects.requireNonNull(postGateway);
    }

    private static Supplier<DomainException> NotFound(final PostID anId) {
        return () -> DomainException.with(new Error("Post with ID %s was not found".formatted(anId.getValue())));
    }

    @Override
    public void execute(final String anIn) {
        final var aPostId = PostID.from(anIn);
        this.postGateway.findById(PostID.from(anIn)).map(DeletePostOutput::from).orElseThrow(NotFound(aPostId));
        this.postGateway.deleteById(PostID.from(anIn));
    }
}
