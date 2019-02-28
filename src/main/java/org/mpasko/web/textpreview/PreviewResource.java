package org.mpasko.web.textpreview;

import org.mpasko.fileTreeModel.TreeGenerator;
import org.mpasko.management.console.DefaultConfig;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.web.DataSourceCache;
import org.mpasko.web.server.JsonTransformer;
import org.mpasko.web.server.StringDTO;

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
                -> new TreeGenerator().generate(DefaultConfig.textSources), new JsonTransformer());

        get(PREVIEW_CONTEXT + "/file", "application/json", (request, response)
                -> previewFile(request.queryParams("id")), new JsonTransformer());
    }

    private StringDTO previewFile(String id) {
        if (id != null && !id.isEmpty() && !id.equalsIgnoreCase("undefined")) {
            return StringDTO.generate(id, idCache, dict);
        } else {
            return new StringDTO("Please choose file");
        }
    }
}
