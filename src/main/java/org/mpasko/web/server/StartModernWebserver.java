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

import java.util.*;

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

        addParameters(Arrays.asList(
            "textpreview",
            "wordspreview",
            "insert",
            "main",
            "search",
            "panel",
            "browse",
            "exam/*/*"
        )).stream()
        .forEach(item -> addStaticRoute(item));
    }

    private static List<String> addParameters(List<String> routes) {
        LinkedList<String> expanded = new LinkedList<>(routes);
        routes.stream().forEach(item -> expanded.add(item+"/*"));
        return expanded;
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
