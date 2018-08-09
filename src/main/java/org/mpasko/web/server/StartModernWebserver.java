/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.web.server;

import org.mpasko.web.BrowserResource;

import static spark.Spark.externalStaticFileLocation;
import static spark.Spark.setPort;

/**
 *
 * @author marcin
 */
public class StartModernWebserver {

    public static void main(String args[]) {
        setPort(8080);
        externalStaticFileLocation("content/frontend/dist/frontend");
        new BrowserResource();
    }
}
