package org.mpasko.japanese.runners.installation;

public class Installer {
    public static void main(String[] args) {
        install();
    }

    public static void install() {
        InitializeConfigFile.initialize();
    }
}
