package org.mpasko.web.preview;

import org.mpasko.console.DefaultConfig;
import org.mpasko.web.DataSourceCache;
import org.mpasko.web.server.JsonTransformer;

import static spark.Spark.get;

public class PreviewResource {
    private static final String PREVIEW_CONTEXT = "/api.v2/preview";

    public PreviewResource(DataSourceCache data) {

    }

    public void setupEndpoints() {
        get(PREVIEW_CONTEXT + "/dir", "application/json", (request, response)
                -> new TreeGenerator().generate(DefaultConfig.globalSources), new JsonTransformer());
    }
}
