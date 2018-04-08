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
    private final ExamsCatalogue exams;

    public BrowserResource() {
        exams = new ExamsCatalogue();
        setupEndpoints();
    }

    private void setupEndpoints() {
        /* *x/
        post(API_CONTEXT, "application/json", (request, response) -> {
            new ExamsCatalogue().getItems());
            response.status(201);
            return response;
        }, new JsonTransformer());

        /* */
        get(BROWSER_CONTEXT, "application/json", (request, response)
                -> exams.getItems(), new JsonTransformer());

        get(BROWSER_CONTEXT + "/item/:id", "application/json", (request, response)
                -> exams.getDataAbout(request.params(":id")), new JsonTransformer());

        get(BROWSER_CONTEXT + "/subitems/:id", "application/json", (request, response)
                -> exams.getSubItems(request.params(":id")), new JsonTransformer());

        get(BROWSER_CONTEXT + "/subitem/:id/:subid", "application/json", (request, response)
                -> exams.getDataAbout(request.params(":id"), request.params(":subid")), new JsonTransformer());

        get(EXAM_CONTEXT + "/item/reading/:id", "application/json", (request, response)
                -> exams.generateReadingExam(request.params(":id")), new JsonTransformer());

        get(EXAM_CONTEXT + "/item/listening/:id", "application/json", (request, response)
                -> exams.generateListeningExam(request.params(":id")), new JsonTransformer());

        get(EXAM_CONTEXT + "/subitem/reading/:id", "application/json", (request, response)
                -> exams.generateReadingSubExam(request.params(":id")), new JsonTransformer());

        get(EXAM_CONTEXT + "/subitem/listening/:id", "application/json", (request, response)
                -> exams.generateListeningSubExam(request.params(":id")), new JsonTransformer());

        put(FILE_CONTEXT + "/white/listening/:id", (request, response)
                -> exams.saveWhiteListeningResults(request.params(":id"), request.body()));

        put(FILE_CONTEXT + "/white/reading/:id", (request, response)
                -> exams.saveWhiteReadingResults(request.params(":id"), request.body()));

        put(FILE_CONTEXT + "/black/listening/:id", (request, response)
                -> exams.saveBlackListeningResults(request.params(":id"), request.body()));

        put(FILE_CONTEXT + "/black/reading/:id", (request, response)
                -> exams.saveBlackReadingResults(request.params(":id"), request.body()));
        /* *x/
        put(API_CONTEXT + "/:id", "application/json", (request, response)
                -> todoService.update(request.params(":id"), request.body()), new JsonTransformer());
        /* */
    }
}
