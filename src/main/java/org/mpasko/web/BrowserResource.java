/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.web;

import org.mpasko.console.DefaultConfig;
import org.mpasko.web.editor.EditorResource;
import org.mpasko.web.examResults.ResultResource;
import org.mpasko.web.generateExamData.ExamDataResource;
import org.mpasko.web.textpreview.FileIdMap;
import org.mpasko.web.textpreview.PreviewResource;
import org.mpasko.web.server.JsonTransformer;
import org.mpasko.web.songAnalysis.Presentation;
import org.mpasko.web.wordspreview.WordsPreviewResource;

import static spark.Spark.*;

/**
 *
 * @author marcin
 */
public class BrowserResource {

    private static final String BROWSER_CONTEXT = "/api/browser";
    private static final String SEARCH_CONTEXT = "/api/search";
    private static final String SONG_ANALYSIS = "/api/songs";
    private final SourceDirectory exams;
    private final FileSearcher search;
    private final Presentation song;
    private final FileIdMap sourceIdCache;

    public BrowserResource() {
        DataSourceCache data = new DataSourceCache();
        sourceIdCache = FileIdMap.generateDefault(DefaultConfig.sources);

        exams = new SourceDirectory(data);
        search = new FileSearcher();
        song = new Presentation();
        setupEndpoints();
        new ResultResource(data).setupEndpoints();
        new ExamDataResource(data).setupEndpoints();
        new PreviewResource(data, sourceIdCache).setupEndpoints();
        new WordsPreviewResource(data).setupEndpoints();
        new SearchResource().setupEndpoints();
        new EditorResource(sourceIdCache).setupEndpoints();
    }

    private void setupEndpoints() {
        get(BROWSER_CONTEXT, "application/json", (request, response)
                -> exams.getItems(), new JsonTransformer());

        get(BROWSER_CONTEXT + "/subitems/:id", "application/json", (request, response)
                -> exams.getSubItems(request.params(":id")), new JsonTransformer());

        get(SEARCH_CONTEXT + "/:id", (request, response)
                -> search.search(request.params(":id")), new JsonTransformer());

        get(SONG_ANALYSIS, (request, response)
                -> song, new JsonTransformer());
    }
}
