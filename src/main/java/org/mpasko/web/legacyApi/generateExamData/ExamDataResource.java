package org.mpasko.web.legacyApi.generateExamData;

import org.mpasko.exams.Combinations;
import org.mpasko.repository.dataSource.DataSourceCache;
import org.mpasko.web.server.JsonTransformer;

import static spark.Spark.get;

@Deprecated
public class ExamDataResource {
    private static final String EXAM_CONTEXT = "/api/exam";
    private static final String BROWSER_CONTEXT = "/api/browser";
    private final ExamsPreparer exams;

    public ExamDataResource(DataSourceCache data) {
        exams = new ExamsPreparer(data);
    }

    @Deprecated
    public void setupEndpoints() {

        get(BROWSER_CONTEXT + "/item/:id",
                "application/json",
                (request, response)
                        -> exams.getDataAbout(request.params(":id")), new JsonTransformer());

        get(BROWSER_CONTEXT + "/subitem/:id/:subid",
                "application/json",
                (request, response)
                        -> exams.getDataAbout(request.params(":id"),
                        request.params(":subid")), new JsonTransformer());

        get(EXAM_CONTEXT + "/item/:activity/:id/:source",
                "application/json",
                (request, response)
                    -> exams.generateExam(request.params(":id"),
                        request.params(":activity"),
                        request.params(":source")), new JsonTransformer());

        get(EXAM_CONTEXT + "/subitem/:activity/:id/:source",
                "application/json",
                (request, response)
                    -> exams.generateSubExam(request.params(":id"),
                        request.params(":activity"),
                        request.params(":source")), new JsonTransformer());

        get(EXAM_CONTEXT + "/combinations",
                "application/json",
                (request, response)
                    -> new Combinations(), new JsonTransformer());
    }
}
