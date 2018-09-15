package org.mpasko.web.textpreview;

import org.mpasko.dictionary.Dictionary;
import org.mpasko.japanese.runners.workflow.SongLayout;

public class PreviewDTO {
    public PreviewDTO(String text) {
        sampleText = text;
    }

    public String sampleText;

    public static PreviewDTO generate(String song, FileIdMap map, Dictionary full_dict) {
        return generate(song, map.search(song), full_dict);
    }

    private static PreviewDTO generate(String song, String filename, Dictionary full_dict) {
        String text = new SongLayout().dryProcess(song, filename, full_dict);
        return new PreviewDTO(text);
    }
}
