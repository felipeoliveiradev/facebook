package com.facebook.modules.Flup.spring.utils;

import com.facebook.modules.Flup.system.utils.SqlUtils;
import org.springframework.data.jpa.domain.Specification;

public final class SpecificationUtils {

    private SpecificationUtils() {
    }

    public static <T> Specification<T> like(final String prop, final String term) {
        return (root, query, cb) -> cb.like(cb.upper(root.get(prop)), SqlUtils.like(term));
    }

    public static <T> Specification<T> likeBoolean(final String prop, final boolean active) {
        return (root, query, cb) -> cb.equal(root.get(prop), active);
    }
}