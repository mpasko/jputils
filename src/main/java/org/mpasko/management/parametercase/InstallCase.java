package org.mpasko.management.parametercase;

import org.mpasko.japanese.runners.installation.Installer;

import java.util.List;

public class InstallCase implements IParameterCase {
    @Override
    public String name() {
        return "install";
    }

    @Override
    public String description() {
        return "Prepares and configures application";
    }

    @Override
    public String category() {
        return "administrative";
    }

    @Override
    public int parametersCount() {
        return 0;
    }

    @Override
    public void doTheJob(List<String> paramValue) {
        Installer.install();
    }
}
