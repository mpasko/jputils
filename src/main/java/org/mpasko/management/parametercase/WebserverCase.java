package org.mpasko.management.parametercase;

import org.mpasko.web.server.StartModernWebserver;

import java.util.List;

public class WebserverCase implements IParameterCase {
    @Override
    public String name() {
        return "webserver";
    }

    @Override
    public String description() {
        return "start webserver";
    }

    @Override
    public String category() {
        return "console";
    }

    @Override
    public int parametersCount() {
        return 0;
    }

    @Override
    public void doTheJob(List<String> paramValue) {
        StartModernWebserver.main(new String[0]);
    }
}
