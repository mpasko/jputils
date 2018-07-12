package org.mpasko.web.examResults;

import org.mpasko.web.DataSourceCache;
import org.mpasko.web.server.JsonParser;

import static spark.Spark.put;

public class ResultResource {
    private static final String FILE_CONTEXT = "/api/save-results";
    private final ResultSaver saver;

    public ResultResource(DataSourceCache data) {
        saver = new ResultSaver(data);
    }

    public void setupEndpoints() {
        put(FILE_CONTEXT + "/:type/:id", (request, response)
                -> saver.saveResults(request.params(":type"),
                request.params(":id"),
                JsonParser.parse(request.body(), ResultData.class)));

    }
}
