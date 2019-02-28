package org.mpasko.web.wordspreview;

import org.mpasko.management.console.DefaultConfig;
import org.mpasko.web.DataSourceCache;
import org.mpasko.web.legacyApi.generateExamData.ExamsPreparer;
import org.mpasko.web.server.JsonTransformer;
import org.mpasko.web.textpreview.FileIdMap;

import static spark.Spark.get;

public class WordsPreviewResource {
    private static final String PREVIEW_CONTEXT = "/api.v2/wordspreview";
    private final ExamsPreparer exams;
    private FileIdMap idCache;

    public WordsPreviewResource(DataSourceCache data) {
        //TODO luckily there are common ids (filenames) between both previews [15.09.2018]
        //..but it would be safer for separate trees for both..
        idCache = FileIdMap.generateDefault(DefaultConfig.wordsGlobalSources);
        exams = new ExamsPreparer(data);
    }

    public void setupEndpoints() {
        get(PREVIEW_CONTEXT, "application/json", (request, response)
                -> exams.getDataByPath(idCache.search(request.queryParams("id"))), new JsonTransformer());

    }
}
