package com.facebook.modules.Category.application.retrieve.list;


import com.facebook.modules.Category.domain.CategoryGateway;
import com.facebook.modules.Flup.system.required.pagination.Pagination;
import com.facebook.modules.Flup.system.required.pagination.SearchQuery;

import java.util.Objects;

public class DefaultListCategoriesUseCase extends ListCategoriesUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultListCategoriesUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Pagination<ListCategoriesOutput> execute(final SearchQuery aQuery) {
        return this.categoryGateway.findAll(aQuery).map(ListCategoriesOutput::from);
    }

}
