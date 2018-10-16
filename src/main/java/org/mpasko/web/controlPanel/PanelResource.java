package org.mpasko.web.controlPanel;

import org.mpasko.management.controlPanel.Controls;
import org.mpasko.web.controlPanel.dao.PanelDao;
import org.mpasko.web.server.JsonTransformer;

import java.util.LinkedList;
import java.util.List;

import static spark.Spark.*;

public class PanelResource {

    private static final String PANEL_CONTEXT = "/api.v2/panel";

    public PanelResource() {

    }

    public void setupEndpoints() {
        get(PANEL_CONTEXT, (request, response)
                -> PanelDao.getWebFormat(), new JsonTransformer());
        post(PANEL_CONTEXT+"/execute/:command", (request, response)
                -> execute(request.params(":command")), new JsonTransformer());
    }

    private static String execute(String command) {
        new Controls().execute(command, new LinkedList<>());
        return "";
    }
}
