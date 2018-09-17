/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.web.server;

import org.mpasko.web.BrowserResource;

import java.util.Arrays;

import static spark.Spark.externalStaticFileLocation;
import static spark.Spark.setPort;
import static spark.Spark.*;

/**
 *
 * @author marcin
 */
public class StartModernWebserver {

    public static void main(String args[]) {
        setPort(8080);
        externalStaticFileLocation("content/frontend/dist/frontend");
        new BrowserResource();

        Arrays.asList(
            "textpreview/*",
            "wordspreview/*",
            "main",
            "search/*"
        ).stream()
        .forEach(item -> addToIgnoreList(item));
    }

    private static void addToIgnoreList(String route) {
        get(route, (request, response)->{
            response.redirect("/");
            return "";
        });
    }
}
