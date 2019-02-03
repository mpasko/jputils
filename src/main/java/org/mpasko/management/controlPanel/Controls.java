package org.mpasko.management.controlPanel;

import org.mpasko.management.parametercase.IParameterCase;
import org.mpasko.management.parametercase.OnyomiGenerationCase;
import org.mpasko.management.parametercase.ParseTextsCase;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Controls {
    public List<IParameterCase> getWebControls() {
        LinkedList<IParameterCase> controls = new LinkedList<IParameterCase>();
        controls.add(new ParseTextsCase());
        controls.add(new OnyomiGenerationCase());
        return controls;
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
