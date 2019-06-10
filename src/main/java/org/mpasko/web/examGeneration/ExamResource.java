package org.mpasko.web.examGeneration;

import org.mpasko.exams.Combinations;
import org.mpasko.configuration.DefaultPaths;
import org.mpasko.japanese.runners.workflow.IDataSource;
import org.mpasko.web.DataSourceCache;
import org.mpasko.web.legacyApi.generateExamData.ExamsPreparer;
import org.mpasko.web.server.JsonTransformer;
import org.mpasko.web.textpreview.FileIdMap;

import static spark.Spark.get;

public class ExamResource {
    private static final String EXAM_CONTEXT = "/api.v2/exam";
    private static final String BROWSER_CONTEXT = "/api.v2/browser";
    private static final String CONFIG_CONTEXT = "/api.v2/combinations";
    private final ExamsPreparer exams;
    private final FileIdMap idCache;

    public ExamResource(IDataSource data) {
        //TODO luckily there are common ids (filenames) between both previews [15.09.2018]
        //..but it would be safer for separate trees for both..
        idCache = FileIdMap.generateDefault(DefaultPaths.wordsGlobalSources);
        exams = new ExamsPreparer(data);
    }

    public void setupEndpoints() {

        get(BROWSER_CONTEXT + "/:id",
                "application/json",
                (request, response)
                        -> exams.getDataByPath(idCache.search(request.params(":id"))), new JsonTransformer());

        get(EXAM_CONTEXT + "/:id/:activity/:phase",
                "application/json",
                (request, response)
                        -> exams.generateExamByPath(idCache.search(request.params(":id")),
                        request.params(":activity"),
                        request.params(":phase")), new JsonTransformer());

        get(CONFIG_CONTEXT,
                "application/json",
                (request, response)
                        -> new Combinations(), new JsonTransformer());
    }
}
