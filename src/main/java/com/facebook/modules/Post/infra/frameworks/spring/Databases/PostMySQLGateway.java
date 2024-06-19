package com.facebook.modules.Post.infra.frameworks.spring.Databases;


import com.facebook.modules.Flup.system.required.helpers.GatewayDefault;
import com.facebook.modules.Flup.system.required.pagination.Pagination;
import com.facebook.modules.Flup.system.required.pagination.SearchQuery;
import com.facebook.modules.Post.domain.Post;
import com.facebook.modules.Post.domain.PostGateway;
import com.facebook.modules.Post.domain.PostID;
import com.facebook.modules.Post.infra.PostDataEntity;
import com.facebook.modules.Post.infra.frameworks.spring.PostRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.facebook.modules.Flup.spring.utils.SpecificationUtils.like;
import static com.facebook.modules.Flup.spring.utils.SpecificationUtils.likeBoolean;

@Component
public class PostMySQLGateway extends GatewayDefault<PostDataEntity> implements PostGateway {
    private final PostRepository repository;

    public PostMySQLGateway(final PostRepository repository) {
        this.repository = repository;
    }

    @Override
    public Post create(Post aPost) {
        return save(aPost);
    }

    @Override
    public void deleteById(PostID anID) {
        final String idValue = anID.getValue();
        if (this.repository.existsById(idValue)) {
            this.repository.deleteById(idValue);
        }
    }

    @Override
    public Optional<Post> findById(PostID anID) {
        return this.repository.findById(anID.getValue()).map(PostDataEntity::toAggregate);
    }

    @Override
    public Post update(final Post aPost) {
        return save(aPost);
    }

    @Override
    public Pagination<Post> findAll(final SearchQuery aQuery) {

        final var specifications = Optional.ofNullable(aQuery.terms())
                .filter(str -> !str.isBlank())
                .map(this::assembleSpecification)
                .orElse(null);


        final var pageResult =
                this.repository.findAll(Specification.where(specifications), page(aQuery));

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(PostDataEntity::toAggregate).toList()
        );
    }

    public Post save(final Post aPost) {
        return this.repository.save(PostDataEntity.from(aPost)).toAggregate();
    }

    private Specification<PostDataEntity> assembleSpecification(final String str) {

        final Specification<PostDataEntity> nameLike = like("name", str);
        final Specification<PostDataEntity> descriptionLike = like("description", str);
        final Specification<PostDataEntity> isActiveLike = likeBoolean("active", true);

        return nameLike.or(descriptionLike).or(isActiveLike);

    }
}
