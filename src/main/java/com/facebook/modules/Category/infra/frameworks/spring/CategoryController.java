package com.facebook.modules.Category.infra.frameworks.spring;


import com.facebook.modules.Category.application.create.CreateCategoryCommand;
import com.facebook.modules.Category.application.create.CreateCategoryOutput;
import com.facebook.modules.Category.application.create.CreateCategoryUseCase;
import com.facebook.modules.Category.application.delete.DeleteCategoryUseCase;
import com.facebook.modules.Category.application.retrieve.byId.CategoryByIdUseCase;
import com.facebook.modules.Category.application.retrieve.list.ListCategoriesUseCase;
import com.facebook.modules.Category.application.update.UpdateCategoryCommand;
import com.facebook.modules.Category.application.update.UpdateCategoryOutput;
import com.facebook.modules.Category.application.update.UpdateCategoryUseCase;
import com.facebook.modules.Category.infra.CategoryAPIPresenter;
import com.facebook.modules.Category.infra.http.request.CreateCategoryRequest;
import com.facebook.modules.Category.infra.http.request.UpdateCategoryRequest;
import com.facebook.modules.Category.infra.http.response.CategoryResponse;
import com.facebook.modules.Category.infra.http.response.ListCategoriesResponse;
import com.facebook.modules.Flup.system.required.pagination.Pagination;
import com.facebook.modules.Flup.system.required.pagination.SearchQuery;
import com.facebook.modules.Flup.system.validation.handlers.Notification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;
import java.util.function.Function;

@RestController
public class CategoryController implements CategoryAPI {

    private final CreateCategoryUseCase categoryUseCase;
    private final CategoryByIdUseCase categoryByIdUseCase;
    private final UpdateCategoryUseCase updateCategoryUseCase;
    private final DeleteCategoryUseCase deleteCategoryUseCase;
    private final ListCategoriesUseCase listCategoriesUseCase;

    public CategoryController(
            final CreateCategoryUseCase categoryUseCase,
            final CategoryByIdUseCase categoryByIdUseCase,
            final UpdateCategoryUseCase updateCategoryUseCase,
            final DeleteCategoryUseCase deleteCategoryUseCase,
            final ListCategoriesUseCase listCategoriesUseCase
    ) {
        this.categoryUseCase = Objects.requireNonNull(categoryUseCase);
        this.categoryByIdUseCase = Objects.requireNonNull(categoryByIdUseCase);
        this.updateCategoryUseCase = updateCategoryUseCase;
        this.deleteCategoryUseCase = deleteCategoryUseCase;
        this.listCategoriesUseCase = listCategoriesUseCase;
    }

    @Override
    public ResponseEntity<?> createCategory(final CreateCategoryRequest input) {

        final CreateCategoryCommand aCommand = CreateCategoryCommand.with(
                input.name(),
                input.active() != null ? input.active() : true
        );

        final Function<Notification, ResponseEntity> onError = ResponseEntity.unprocessableEntity()::body;
        final Function<CreateCategoryOutput, ResponseEntity> onSuccess = output -> ResponseEntity.created(URI.create("/Categories/" + output.id())).body(output);
        return this.categoryUseCase.execute(aCommand).fold(onError, onSuccess);
    }

    @Override
    public Pagination<ListCategoriesResponse> listCategories(
            final String search,
            int page,
            int perPage,
            final String sort,
            final String direction
    ) {
        return listCategoriesUseCase.execute(new SearchQuery(page, perPage, search, sort, direction)).map(CategoryAPIPresenter::present);
    }

    @Override
    public CategoryResponse getById(final String id) {
        return CategoryAPIPresenter.present(this.categoryByIdUseCase.execute(id));
    }

    @Override
    public ResponseEntity<?> updateById(final String id, final UpdateCategoryRequest input) {
        final UpdateCategoryCommand aCommand = UpdateCategoryCommand.with(
                id,
                input.name(),
                input.active() != null ? input.active() : true
        );

        final Function<Notification, ResponseEntity<?>> onError = ResponseEntity.unprocessableEntity()::body;
        final Function<UpdateCategoryOutput, ResponseEntity<?>> onSuccess = ResponseEntity::ok;
        return this.updateCategoryUseCase.execute(aCommand).fold(onError, onSuccess);
    }

    @Override
    public void deleteById(final String id) {
        this.deleteCategoryUseCase.execute(id);
    }
}
