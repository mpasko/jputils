package org.mpasko.management.parametercase;

import org.mpasko.japanese.runners.workflow.GenerateOnyomiWhitelist;

import java.util.List;

public class OnyomiGenerationCase implements IParameterCase {
    @Override
    public String name() {
        return "onyomi";
    }

    @Override
    public String description() {
        return "Regenerate onyomi prediction list";
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
        new GenerateOnyomiWhitelist().start();
    }
}
