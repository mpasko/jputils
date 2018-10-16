package org.mpasko.management.controlPanel;

import org.mpasko.management.parametercase.IParameterCase;
import org.mpasko.management.parametercase.ParseTextsCase;

import java.util.Arrays;
import java.util.List;

public class Controls {
    public List<IParameterCase> getWebControls() {
        return Arrays.asList(new ParseTextsCase());
    }

    public void execute(String command, List<String> params) {
        IParameterCase executor = getWebControls()
                .stream()
                .filter(control -> control.name()
                        .equalsIgnoreCase(command))
                .findAny()
                .get();
        executor.doTheJob(params);
    }
}
