package org.mpasko.web.quizDownload;

import org.mpasko.repository.dataSource.IDataSource;
import org.mpasko.web.server.JsonTransformer;
import org.mpasko.web.textpreview.FileIdMap;

import static spark.Spark.get;

public class QuizResource {
    private static String QUIZ_CONTEXT = "/api.v2/quizdownload";
    private final QuizPreparer preparer;

    public QuizResource(FileIdMap idCache, IDataSource data) {
        this.preparer = new QuizPreparer(idCache, data);
    }

    public void setupEndpoints() {
        get(QUIZ_CONTEXT+"/:id/:activity/:phase", "application/json", (request, response)
                -> preparer.prepareQuiz(request.params(":id"), request.params(":activity"), request.params(":phase")),
                new JsonTransformer());
    }
}
