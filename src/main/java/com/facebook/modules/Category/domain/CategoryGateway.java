package com.facebook.modules.Category.domain;

import com.facebook.modules.Flup.system.required.pagination.Pagination;
import com.facebook.modules.Flup.system.required.pagination.SearchQuery;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

public interface CategoryGateway {
    Category create(Category aCategory) throws IOException, TimeoutException;

    void deleteById(CategoryID anID);

    Optional<Category> findById(CategoryID anID);

    Category update(Category aCategory);

    Pagination<Category> findAll(SearchQuery aQuery);

    List<CategoryID> existsByIds(Iterable<CategoryID> ids);

}
