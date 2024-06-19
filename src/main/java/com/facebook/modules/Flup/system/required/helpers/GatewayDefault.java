package com.facebook.modules.Flup.system.required.helpers;

import com.facebook.modules.Flup.system.required.pagination.SearchQuery;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;


public abstract class GatewayDefault<T> {
    protected PageRequest page(final SearchQuery aQuery) {
        return PageRequest.of(
                aQuery.page(),
                aQuery.perPage(),
                Sort.by(Sort.Direction.fromString(aQuery.direction()), aQuery.sort())
        );
    }
}
