package com.facebook.modules.Category.infra.frameworks.spring;

import com.facebook.modules.Category.infra.CategoryDataEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryDataEntity, String> {
    Page<CategoryDataEntity> findAll(Specification<CategoryDataEntity> whereClause, Pageable page);

    @Query(value = "select c.id from Category c where c.id in :ids")
    List<String> existsByIds(@Param("ids") List<String> ids);
}