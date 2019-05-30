package org.mpasko.japanese.runners.parsers.subtitles.importers;

import org.mpasko.commons.Classifier;
import org.mpasko.japanese.runners.parsers.subtitles.ImportAllFrom;

public class ImportSrt {
    public void importAllSrt() {
        String base = "/home/marcin/repo/Wlasne/Nihongo/subtitles/srt_format";
        new ImportAllFrom().importAllFrom(base, this::importSrtFile);
    }

    private String importSrtFile(String raw) {
        StringBuilder result = new StringBuilder();
        for (String line : raw.split("\n")) {
            if (Classifier.classify(line).containsJapanese()) {
                result.append("\n").append(line);
            }
        }
        return result.toString();
    }
}
