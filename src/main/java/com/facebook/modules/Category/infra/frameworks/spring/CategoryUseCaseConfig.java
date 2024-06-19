package com.facebook.modules.Category.infra.frameworks.spring;

import com.facebook.modules.Category.application.create.CreateCategoryUseCase;
import com.facebook.modules.Category.application.create.DefaultCreateCategoryUseCase;
import com.facebook.modules.Category.application.delete.DefaultDeleteCategoryUseCase;
import com.facebook.modules.Category.application.delete.DeleteCategoryUseCase;
import com.facebook.modules.Category.application.retrieve.byId.CategoryByIdUseCase;
import com.facebook.modules.Category.application.retrieve.byId.DefaultCategoryByIdUseCase;
import com.facebook.modules.Category.application.retrieve.list.DefaultListCategoriesUseCase;
import com.facebook.modules.Category.application.retrieve.list.ListCategoriesUseCase;
import com.facebook.modules.Category.application.update.DefaultUpdateCategoryUseCase;
import com.facebook.modules.Category.application.update.UpdateCategoryUseCase;
import com.facebook.modules.Category.domain.CategoryGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryUseCaseConfig {
    private final CategoryGateway categoryGateway;

    public CategoryUseCaseConfig(final CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Bean
    public CreateCategoryUseCase createCategoryUseCase() {
        return new DefaultCreateCategoryUseCase(categoryGateway);
    }

    @Bean
    public UpdateCategoryUseCase updateCategoryUseCase() {
        return new DefaultUpdateCategoryUseCase(categoryGateway);
    }

    @Bean
    public DeleteCategoryUseCase deleteCategoryUseCase() {
        return new DefaultDeleteCategoryUseCase(categoryGateway);
    }

    @Bean
    public CategoryByIdUseCase CategoryByIdUseCase() {
        return new DefaultCategoryByIdUseCase(categoryGateway);
    }

    @Bean
    public ListCategoriesUseCase listCategoriesUseCase() {
        return new DefaultListCategoriesUseCase(categoryGateway);
    }
}
