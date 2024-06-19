package com.facebook.modules.Post.application.retrieve.list;


import com.facebook.modules.Category.application.retrieve.byId.CategoryOutput;
import com.facebook.modules.Category.domain.CategoryGateway;
import com.facebook.modules.Category.domain.CategoryID;
import com.facebook.modules.Category.infra.CategoryAPIPresenter;
import com.facebook.modules.Flup.system.required.pagination.Pagination;
import com.facebook.modules.Flup.system.required.pagination.SearchQuery;
import com.facebook.modules.Post.domain.PostGateway;

import java.util.ArrayList;
import java.util.Objects;

public class DefaultListPostsUseCase extends ListPostsUseCase {
    private final CategoryGateway categoryGateway;

    private final PostGateway postGateway;

    public DefaultListPostsUseCase(final CategoryGateway categoryGateway, final PostGateway postGateway) {
        this.postGateway = Objects.requireNonNull(postGateway);
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Pagination<ListPostsOutput> execute(final SearchQuery aQuery) {
        return this.postGateway.findAll(aQuery).map(it -> {
            ArrayList<Object[]> teste = new ArrayList<>();
            it.getCategories().stream().map(it2 -> teste.add(this.categoryGateway.findById(CategoryID.from(it2.getValue())).stream().map(it3 -> CategoryAPIPresenter.present(CategoryOutput.from(it3))).toArray())).toList();
            return new ListPostsOutput(it.getId(), it.getName(), it.getDescription(), teste, it.isActive(), it.getCreatedAt(), it.getUpdatedAt(), it.getDeletedAt());
        });
    }

    ;

}
