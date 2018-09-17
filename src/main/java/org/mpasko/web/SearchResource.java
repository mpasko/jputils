package org.mpasko.web;

import org.mpasko.web.server.JsonTransformer;

import static spark.Spark.get;

public class SearchResource {
    private static final String SEARCH_CONTEXT = "/api.v2/search";
    private final FileSearcher search;

    public SearchResource() {
        search = new FileSearcher();
    }

    public void setupEndpoints() {
        get(SEARCH_CONTEXT, (request, response)
                -> search.search(request.queryParams("id")), new JsonTransformer());
    }
}
