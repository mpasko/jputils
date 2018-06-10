/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.runners.workflow;

import java.util.Arrays;
import java.util.stream.Collectors;
import org.mpasko.console.DefaultConfig;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.dictionary.DictionaryReconstructor;
import org.mpasko.japanese.wordcomparison.StrictSynonimeComparer;
import org.mpasko.japanese.wordfilters.GenericFilter;
import org.mpasko.japanese.wordfilters.InversionOf;
import org.mpasko.japanese.wordfilters.ItemExistsInDictionary;
import org.mpasko.util.Filesystem;

/**
 *
 * @author marcin
 */
public class Remover {

    private Dictionary fullDictionary;

    public Remover(Dictionary fullDictionary) {
        this.fullDictionary = fullDictionary;
    }

    public void removeRedundancy(String source, String type, String data) {
        String root_path = switchSourcePath(source, type);
        new Filesystem().getSubfiles(root_path)
                .stream()
                .forEach(file -> removeInFile(root_path + file, type, data));
    }

    public static String switchSourcePath(String source, String type) {
        String root_path;
        if (source.equalsIgnoreCase("white")) {
            if (type.equalsIgnoreCase("listening")) {
                root_path = DefaultConfig.listeningWhitelist;
            } else {
                root_path = DefaultConfig.readingWhitelist;
            }
        } else {
            if (type.equalsIgnoreCase("listening")) {
                root_path = DefaultConfig.listeningBlacklist;
            } else {
                root_path = DefaultConfig.readingBlacklist;
            }
        }
        return root_path;
    }

    private void removeInFile(String path, String type, String data) {
        DictionaryReconstructor reconstructor = switchReconstructor(type);
        final String currentState = new Filesystem().loadFile(path);
        Dictionary toRemove = reconstructor.reconstruct(data);
        final String updatedState = Arrays.stream(currentState.split("\n"))
                .filter(line -> lineShouldRemain(line, toRemove, reconstructor))
                .collect(Collectors.joining("\n"));
        new Filesystem().saveFile(path, updatedState);
    }

    private DictionaryReconstructor switchReconstructor(String type) {
        if (type.equalsIgnoreCase("listening")) {
            return DictionaryReconstructor.StandardListeningReconstructor(fullDictionary);
        } else {
            return DictionaryReconstructor.StandardReadingReconstructor(fullDictionary);
        }
    }

    private boolean lineShouldRemain(String line, Dictionary toRemove, DictionaryReconstructor reconstructor) {
        Dictionary reconstructedLine = reconstructor.reconstruct(line);
        return notIn(toRemove).filter(reconstructedLine).getDict().size() > 0;
    }

    //TODO consolidate with ExamData
    private GenericFilter notIn(Dictionary dict) {
        return new InversionOf(in(dict));
    }

    private GenericFilter in(Dictionary dict) {
        return new ItemExistsInDictionary(dict.getDict(), new StrictSynonimeComparer());
    }
}
