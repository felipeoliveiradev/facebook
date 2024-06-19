package com.facebook.modules.Post.infra.frameworks.spring;

import com.facebook.modules.Post.infra.PostDataEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostDataEntity, String> {
    Page<PostDataEntity> findAll(Specification<PostDataEntity> whereClause, Pageable page);
}
