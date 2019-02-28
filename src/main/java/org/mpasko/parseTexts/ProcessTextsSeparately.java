package org.mpasko.parseTexts;

import org.mpasko.dictionary.Dictionary;
import org.mpasko.loadres.JmDictLoader;

public class ProcessTextsSeparately {

    public static void main(String argv[]) {
        new ProcessTextsSeparately().start();
    }

    public void start() {
        Dictionary fullDictionary = JmDictLoader.loadDictionary();
        new ExtractWordsFromTexts(fullDictionary).start();
        new GenerateReading(fullDictionary).start();
    }

}
