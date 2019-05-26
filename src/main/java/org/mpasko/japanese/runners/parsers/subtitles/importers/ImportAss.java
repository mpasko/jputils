package org.mpasko.japanese.runners.parsers.subtitles.importers;

import org.mpasko.japanese.runners.parsers.subtitles.ImportAllFrom;

public class ImportAss {
    public void importAllAss() {
        String base = "/home/marcin/repo/Wlasne/Nihongo/subtitles/ass_format";
        new ImportAllFrom().importAllFrom(base, this::importAssFile);
    }

    private String importAssFile(String raw) {
        StringBuilder result = new StringBuilder();
        for (String line : raw.split("\n")) {
            if (line.toLowerCase().contains("dialogue")) {
                String pure = extractTextFromAss(line);
                result.append("\n").append(pure);
            }
        }
        return result.toString();
    }

    private String extractTextFromAss(String line) {
        String[] collumns = line.split(",");
        String styled = collumns[collumns.length - 1];
        String almostPure = styled.replaceAll("\\{.*\\}", "");
        String[] curlDivided = almostPure.split("}");
        return curlDivided[curlDivided.length-1];
    }
}
