package org.mpasko.web.editor;

import org.mpasko.editor.Asset;
import org.mpasko.repository.EditorInterface;
import org.mpasko.web.server.JsonParser;
import org.mpasko.web.server.JsonTransformer;
import org.mpasko.web.textpreview.FileIdMap;

import java.io.IOException;

import static spark.Spark.*;

public class EditorResource {
    private static final String EDITOR = "/api.v2/editor";
    private final EditorInterface editor;
    private final FileIdMap idCache;

    public EditorResource(FileIdMap idCache) {
        this.idCache = idCache;
        editor = new EditorInterface();
    }

    public void setupEndpoints() {
        get(EDITOR, (request, response)
                -> editor.open(request.queryParams("id"), idCache), new JsonTransformer());
        put(EDITOR, (request, response)
                -> putRequest(editor, request.body(), idCache), new JsonTransformer());
    }

    private static Asset putRequest(EditorInterface editor, String body, FileIdMap idCache)
            throws IOException {
        Asset asset = JsonParser.parse(body, Asset.class);
        editor.save(asset.directory, asset.name, asset, idCache);
        return asset;
    }
}
