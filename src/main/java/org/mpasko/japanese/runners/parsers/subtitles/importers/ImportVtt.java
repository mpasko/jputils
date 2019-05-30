package org.mpasko.japanese.runners.parsers.subtitles.importers;

import org.mpasko.japanese.runners.parsers.subtitles.ImportAllFrom;

public class ImportVtt {
    public void importAllVtt() {
        String base = "/home/marcin/repo/Wlasne/Nihongo/subtitles/vtt_format";
        new ImportAllFrom().importAllFrom(base, this::importVttFile);
    }

    private String importVttFile(String raw) {
        StringBuilder result = new StringBuilder();
        for (String line : raw.split("\n")) {
            if (!line.contains("-->") && line.length() > 2) {
                String pure = extractTextFromVtt(line);
                result.append("\n").append(pure);
            }
        }
        return result.toString();
    }

    private String extractTextFromVtt(String line) {
        return line.replaceAll("<rp>.*</rp>", "")
                .replaceAll("<rt>.*</rt>", "")
                .replaceAll("<ruby>", "")
                .replaceAll("</ruby>", "");
    }
}
