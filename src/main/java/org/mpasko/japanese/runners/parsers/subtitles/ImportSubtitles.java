package org.mpasko.japanese.runners.parsers.subtitles;

import org.mpasko.japanese.runners.parsers.subtitles.importers.ImportAss;
import org.mpasko.japanese.runners.parsers.subtitles.importers.ImportSrt;
import org.mpasko.japanese.runners.parsers.subtitles.importers.ImportVtt;

public class ImportSubtitles {
    public static void main(String[] args) {
        new ImportSrt().importAllSrt();
        new ImportAss().importAllAss();
        new ImportVtt().importAllVtt();
    }
}
