package org.mpasko.japanese.runners.parsers;

import org.mpasko.commons.Furiganiser;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.dictionary.IDictionary;
import org.mpasko.util.Filesystem;
import org.mpasko.util.StringUtils;

public class ImportJlptGrammar {
    public static void main(String[] args) {
        new ImportJlptGrammar().start();
    }

    public void start() {
        String raw = new Filesystem().loadFile("inputs/jlpt_grammar_raw.txt");
        IDictionary dict = new Dictionary();
        for (String line : raw.split("\n")) {
            String kanji = StringUtils.clear(line.split(" ")[0]);
            String romaji = StringUtils.clear(extractExpressionInParents(line)).replaceAll(" ", "");
            String hiragana = new Furiganiser().furiganise(romaji);
            String meaning = StringUtils.clear(line.split(":")[1].trim());
            dict.put(kanji, hiragana, meaning);
        }
        dict.write("dictionaries/jlpt_grammar.txt");
    }

    private String extractExpressionInParents(String line) {
        String beforeRightParent = line.split("\\)")[0];
        return beforeRightParent.split("\\(")[1];
    }
}
