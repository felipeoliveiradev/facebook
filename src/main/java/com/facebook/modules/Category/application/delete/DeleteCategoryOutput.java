package com.facebook.modules.Category.application.delete;

import com.facebook.modules.Category.domain.Category;
import com.facebook.modules.Category.domain.CategoryID;

public record DeleteCategoryOutput(
        CategoryID id
) {
    public static DeleteCategoryOutput from(final Category aCategory) {
        return new DeleteCategoryOutput(
                aCategory.getId()
        );
    }
}
