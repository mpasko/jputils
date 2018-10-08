/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.web.server;

import org.mpasko.web.BrowserResource;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.externalStaticFileLocation;
import static spark.Spark.setPort;
import static spark.Spark.*;

/**
 *
 * @author marcin
 */
public class StartModernWebserver {

    public static void main(String args[]) {
        port(8080);
        externalStaticFileLocation("content/frontend/dist/frontend");
        new BrowserResource();

        Arrays.asList(
            "textpreview",
            "wordspreview",
            "textpreview/*",
            "wordspreview/*",
            "main",
            "search/*"
        ).stream()
        .forEach(item -> addStaticRoute(item));
    }

    private static void addStaticRoute(String route) {
        Map map = new HashMap();
        FileTemplateResolver templateResolver = generateTemplateResolver();
        get(route, (rq, rs) ->
                new ModelAndView(map, "index"),
                new ThymeleafTemplateEngine(templateResolver));
    }

    private static FileTemplateResolver generateTemplateResolver() {
        FileTemplateResolver templateResolver = new FileTemplateResolver();
        templateResolver.setPrefix("content/frontend/dist/frontend/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setCacheable(false);
        return templateResolver;
    }
}
