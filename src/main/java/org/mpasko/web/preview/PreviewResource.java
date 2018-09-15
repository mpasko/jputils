package org.mpasko.web.preview;

import org.mpasko.console.DefaultConfig;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.web.DataSourceCache;
import org.mpasko.web.server.JsonTransformer;

import static spark.Spark.get;

public class PreviewResource {
    private static final String PREVIEW_CONTEXT = "/api.v2/preview";
    private FileIdMap idCache;
    private Dictionary dict;

    public PreviewResource(DataSourceCache data) {
        idCache = FileIdMap.generateDefault(DefaultConfig.sources);
        dict = data.dataSources.getGlobalDict();
    }

    public void setupEndpoints() {
        get(PREVIEW_CONTEXT + "/dir", "application/json", (request, response)
                -> new TreeGenerator().generate(DefaultConfig.sources), new JsonTransformer());

        get(PREVIEW_CONTEXT + "/file", "application/json", (request, response)
                -> PreviewDTO.generate(request.queryParams("id"), idCache, dict), new JsonTransformer());
    }
}
