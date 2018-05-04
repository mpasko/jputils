/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.web;

import static spark.Spark.*;

/**
 *
 * @author marcin
 */
public class BrowserResource {

    private static final String BROWSER_CONTEXT = "/api/browser";
    private static final String EXAM_CONTEXT = "/api/exam";
    private static final String FILE_CONTEXT = "/api/save";
    private static final String SEARCH_CONTEXT = "/api/search";
    private final ExamsCatalogue exams;
    private final FileSearcher search;

    public BrowserResource() {
        exams = new ExamsCatalogue();
        search = new FileSearcher();
        setupEndpoints();
    }

    private void setupEndpoints() {
        get(BROWSER_CONTEXT, "application/json", (request, response)
                -> exams.getItems(), new JsonTransformer());

        get(BROWSER_CONTEXT + "/item/:id", "application/json", (request, response)
                -> exams.getDataAbout(request.params(":id")), new JsonTransformer());

        get(BROWSER_CONTEXT + "/subitems/:id", "application/json", (request, response)
                -> exams.getSubItems(request.params(":id")), new JsonTransformer());

        get(BROWSER_CONTEXT + "/subitem/:id/:subid", "application/json", (request, response)
                -> exams.getDataAbout(request.params(":id"), request.params(":subid")), new JsonTransformer());

        get(EXAM_CONTEXT + "/item/reading/:id/:source", "application/json", (request, response)
                -> exams.generateReadingExam(request.params(":id"), request.params(":source")), new JsonTransformer());

        get(EXAM_CONTEXT + "/item/listening/:id/:source", "application/json", (request, response)
                -> exams.generateListeningExam(request.params(":id"), request.params(":source")), new JsonTransformer());

        get(EXAM_CONTEXT + "/subitem/reading/:id/:source", "application/json", (request, response)
                -> exams.generateReadingSubExam(request.params(":id"), request.params(":source")), new JsonTransformer());

        get(EXAM_CONTEXT + "/subitem/listening/:id/:source", "application/json", (request, response)
                -> exams.generateListeningSubExam(request.params(":id"), request.params(":source")), new JsonTransformer());

        put(FILE_CONTEXT + "/:source/:type/:id", (request, response)
                -> exams.saveResults(request.params(":source"), request.params(":type"), request.params(":id"), request.body()));

        get(SEARCH_CONTEXT + "/:id", (request, response)
                -> search.search(request.params(":id")), new JsonTransformer());
    }
}
