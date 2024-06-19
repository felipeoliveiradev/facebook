package com.facebook.modules.Post.application.retrieve.list;

import com.facebook.modules.Flup.system.required.helpers.UseCase;
import com.facebook.modules.Flup.system.required.pagination.Pagination;
import com.facebook.modules.Flup.system.required.pagination.SearchQuery;

public abstract class ListPostsUseCase extends UseCase<SearchQuery, Pagination<ListPostsOutput>> {

}
