package com.facebook.modules.Category.infra;

import com.facebook.modules.Category.application.retrieve.byId.CategoryOutput;
import com.facebook.modules.Category.application.retrieve.list.ListCategoriesOutput;
import com.facebook.modules.Category.infra.http.response.CategoryResponse;
import com.facebook.modules.Category.infra.http.response.ListCategoriesResponse;

public interface CategoryAPIPresenter {

    static CategoryResponse present(final CategoryOutput output) {
        return new CategoryResponse(
                output.id(),
                output.name(),
                output.isActive(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }

    static ListCategoriesResponse present(final ListCategoriesOutput output) {
        return new ListCategoriesResponse(
                output.id().getValue(),
                output.name(),
                output.isActive(),
                output.updatedAt(),
                output.createdAt(),
                output.deletedAt()
        );
    }
}
