/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.web.server;

import org.mpasko.web.BrowserResource;

import static spark.Spark.*;

/**
 *
 * @author marcin
 */
public class StartWebserver {

    public static void main(String args[]) {
        setPort(8081);
        externalStaticFileLocation("content");
        new BrowserResource();
    }
}
