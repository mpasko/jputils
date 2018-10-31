package org.mpasko.web.server;

import org.mpasko.dictionary.Dictionary;
import org.mpasko.parseTexts.SongLayout;
import org.mpasko.web.textpreview.FileIdMap;

public class StringDTO {
    public StringDTO(String text) {
        sampleText = text;
    }

    public String sampleText;

    public static StringDTO generate(String song, FileIdMap map, Dictionary full_dict) {
        return generate(song, map.search(song), full_dict);
    }

    private static StringDTO generate(String song, String filename, Dictionary full_dict) {
        String text = new SongLayout().generateChunksForFile(song, filename, full_dict);
        return new StringDTO(text);
    }
}
