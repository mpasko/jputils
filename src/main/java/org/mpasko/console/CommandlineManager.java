/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.console;

import java.util.Arrays;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.mpasko.japanese.runners.exams.GenerateExams;
import org.mpasko.japanese.runners.parsers.ParseJishoOutputs;

/**
 *
 * @author marcin
 */
public class CommandlineManager {

    private CommandLineParser parser = new BasicParser();
    private Options options = new Options();

    public void execute(String[] argv) {
        setupCommands();
        executeCase(argv);
    }

    private void setupCommands() {
        options.addOption("S", "songs", false, "Regenerate songs");
        options.addOption("E", "exam", true, "Create Exam");
    }

    private void executeCase(String[] argv) {
        try {
            CommandLine cmd = parser.parse(options, argv);
            if (cmd.hasOption("songs")) {
                ParseJishoOutputs.processAllSongs();
            } else if (cmd.hasOption("exam")) {
                String source = cmd.getOptionValue("exam");
                GenerateExams.processTripleDict(source, null);
            } else {
                System.out.println(String.format("Unknown command: %s", Arrays.toString(argv)));
            }
        } catch (ParseException ex) {
            System.out.println(String.format("Bad command: %s", ex.getMessage()));
        }
    }
}
