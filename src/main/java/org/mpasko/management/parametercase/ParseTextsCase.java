package org.mpasko.management.parametercase;

import org.mpasko.parseTexts.ProcessTextsSeparately;

import java.util.List;

public class ParseTextsCase implements IParameterCase{
    @Override
    public String name() {
        return "parse";
    }

    @Override
    public String description() {
        return "Parse all texts";
    }

    @Override
    public String category() {
        return "processing";
    }

    @Override
    public int parametersCount() {
        return 0;
    }

    @Override
    public void doTheJob(List<String> paramValue) {
        new ProcessTextsSeparately().start();
    }
}
