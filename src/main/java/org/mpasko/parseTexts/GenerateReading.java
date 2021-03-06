package org.mpasko.parseTexts;

import org.mpasko.configuration.DefaultPaths;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.dictionary.IDictionary;
import org.mpasko.editor.Asset;
import org.mpasko.util.Filesystem;
import org.mpasko.util.Traverser;

import java.util.List;
import java.util.stream.Collectors;

public class GenerateReading {

    private final Dictionary fullDictionary;
    private ExtractorFilter extractorFilter;

    public GenerateReading(Dictionary fullDict) {
        this.fullDictionary = fullDict;
        this.extractorFilter = new ExtractorFilter();
    }

    public void start() {
        Traverser.traverse(DefaultPaths.wordsGlobalSources, this::visit);
    }

    private void visit(String _b, String relative, String name) {
        if (!name.contains(".eng")) {
            generateReadingForFile(relative, name, this.fullDictionary);
        }
    }

    private void generateReadingForFile(String relative, String name, Dictionary fullDict) {
        //String dictSourceFullPath = String.format("%s/%s/%s", DefaultPaths.wordsGlobalSources, relative, name);
        String textFullPath = String.format("%s/%s/%s", DefaultPaths.textSources, relative, name);
        String resultPath = String.format("%s/%s/%s", DefaultPaths.readingOutput, relative, name);
        //Dictionary dictionary = new DictionaryFileLoader().loadTripleDict(dictSourceFullPath);
        String content = generateChunked(name, Asset.load(textFullPath), fullDict);
        new Filesystem().saveFile(resultPath, content);
    }

    public String generateChunked(String title, Asset source, Dictionary global_dict) {
        StringBuilder result = new StringBuilder("[" + title + "]").append("\n");
        for (String chunk : new SplitText().splitEvenly(source.japanese)) {
            result.append(formatChunk(chunk, global_dict)).append("\n");
        }
        result.append(source.english).append("\n");
        return result.toString();
    }

    private String formatChunk(String sourceText, Dictionary global_dict) {
        final String dictionaryStringified = findAndFilterWords(sourceText, global_dict);
        return new StringBuilder(dictionaryStringified).append("\n")
                .append(sourceText)
                .toString();
    }

    private String findAndFilterWords(String sourceText, Dictionary full_dict) {
        List<String> words = findWords(sourceText, full_dict);
        IDictionary filtered = this.extractorFilter.findAndFilterItemsFromDictionary(words, full_dict);
        return filtered.toString();
    }

    private static List<String> findWords(String jap, Dictionary dict) {
        return new WordsExtractor(dict)
                .extractFromText(jap)
                .stream()
                .map(entry -> entry.serializedKeywords())
                .collect(Collectors.toList());
    }
}
