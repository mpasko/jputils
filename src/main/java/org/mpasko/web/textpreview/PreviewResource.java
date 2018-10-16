package org.mpasko.web.textpreview;

import org.mpasko.management.console.DefaultConfig;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.web.DataSourceCache;
import org.mpasko.web.server.JsonTransformer;

import static spark.Spark.get;

public class PreviewResource {
    private static final String PREVIEW_CONTEXT = "/api.v2/textpreview";
    private FileIdMap idCache;
    private Dictionary dict;

    public PreviewResource(DataSourceCache data, FileIdMap sourceIds) {
        this.idCache = sourceIds;
        dict = data.dataSources.getGlobalDict();
    }

    public void setupEndpoints() {
        get(PREVIEW_CONTEXT + "/dir", "application/json", (request, response)
                -> new TreeGenerator().generate(DefaultConfig.sources), new JsonTransformer());

        get(PREVIEW_CONTEXT + "/file", "application/json", (request, response)
                -> previewFile(request.queryParams("id")), new JsonTransformer());
    }

    private PreviewDTO previewFile(String id) {
        if (id != null && !id.isEmpty() && !id.equalsIgnoreCase("undefined")) {
            return PreviewDTO.generate(id, idCache, dict);
        } else {
            return new PreviewDTO("Please choose file");
        }
    }
}
