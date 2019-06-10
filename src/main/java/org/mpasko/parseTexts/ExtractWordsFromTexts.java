package org.mpasko.parseTexts;

import org.mpasko.commons.DictEntry;
import org.mpasko.configuration.DefaultPaths;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.dictionary.IDictionary;
import org.mpasko.util.Filesystem;
import org.mpasko.util.Traverser;

import java.util.List;
import java.util.stream.Collectors;

public class ExtractWordsFromTexts {
    private Dictionary fullDictionary;

    public ExtractWordsFromTexts(Dictionary fullDictionary) {
        this.fullDictionary = fullDictionary;
    }

    public void start() {
        String sourceBasePath = "improved_workflow/texts";
        Traverser.traverse(sourceBasePath, this::process);
    }

    private void process(String basePath, String relativePath, String filename) {
        String actualPath = basePath+"/"+relativePath+"/"+filename;
        String jap = new Filesystem().loadFile(actualPath);
        final String dictionaryStringified = findAndFilterWords(jap, this.fullDictionary);
        new Filesystem().saveFile(String.format("./%s/%s/%s", DefaultPaths.wordsGlobalSources, relativePath, filename), dictionaryStringified);
    }

    private String findAndFilterWords(String sourceText, Dictionary full_dict) {
        List<DictEntry> words = findWords(sourceText, full_dict);
        IDictionary filtered = new Dictionary(words);
        return filtered.toString();
    }

    private static List<DictEntry> findWords(String jap, Dictionary dict) {
        return new WordsExtractor(dict)
                .extractFromText(jap)
                .stream()
                .collect(Collectors.toList());
    }
}
