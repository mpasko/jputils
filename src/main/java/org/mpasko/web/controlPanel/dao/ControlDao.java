package org.mpasko.web.controlPanel.dao;

import org.mpasko.management.parametercase.IParameterCase;

public class ControlDao {
    public String name;
    public String description;

    public static ControlDao convertIntoWebFormat(IParameterCase control) {
        ControlDao webFormat = new ControlDao();
        webFormat.description = control.description();
        webFormat.name = control.name();
        return webFormat;
    }
}
