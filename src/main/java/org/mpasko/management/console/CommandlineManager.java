/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.management.console;

import java.util.Arrays;
import java.util.LinkedList;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.mpasko.management.parametercase.*;

/**
 *
 * @author marcin
 */
public class CommandlineManager {

    private CommandLineParser parser = new BasicParser();
    private Options options = new Options();
    private LinkedList<ParameterCaseVerifier> cases = new LinkedList<>();

    public void execute(String[] argv) {
        setupCommands();
        executeCase(argv);
    }

    private void setupCommands() {
        add(new InstallCase());
        add(new ExamCase());
        add(new SongsCase());
        add(new DictionaryCase());
        add(new WorkflowCase());
        add(new WebserverCase());
        cases.forEach(param -> param.setup(options));
    }

    private void add(IParameterCase param) {
        cases.add(new ParameterCaseVerifier(param));
    }

    private void executeCase(String[] argv) {
        try {
            CommandLine cmd = parser.parse(options, argv);
            boolean anyMatched = cases.stream().anyMatch(param -> param.doJobIfNeeded(cmd));
            if (!anyMatched) {
                System.out.println(String.format("Unknown command: %s", Arrays.toString(argv)));
                printHelp();
            }
        } catch (ParseException ex) {
            System.out.println(String.format("Bad command: %s", ex.getMessage()));
        }
    }

    private void printHelp() {
        System.out.println("Possible commands:");
        cases.stream().forEach(param -> System.out.println(param.getDescription()));
    }
}
