package org.mpasko.japanese.runners.parsers.subtitles;

import org.mpasko.util.Filesystem;
import org.mpasko.util.StringUtils;

import java.util.List;

public class ImportAllFrom {
    public void importAllFrom(String base, Importer importer) {
        List<String> franchieses = new Filesystem().getSubdirectories(base);
        for(String franchiese : franchieses) {
            importFranchiese(base, franchiese, importer);
        }
    }

    private void importFranchiese(String base, String franchiese, Importer importer) {
        System.out.println("Importing: "+franchiese);
        String franchieseBase = base + "/" + franchiese;
        List<String> chapters = new Filesystem().getSubfiles(franchieseBase);
        for(String chapter : chapters) {
            importChapter(base, franchiese, chapter, importer);
        }
    }

    private void importChapter(String base, String franchiese, String chapter, Importer importer) {
        String headerPathIndicator = franchiese + "/" + chapter;
        String sourcePath = base + "/" + franchiese + "/" + chapter;
        String destinationPath = "improved_workflow/texts/subtitles/"+StringUtils.clear(headerPathIndicator);
        importChapterFromTo(sourcePath, destinationPath, headerPathIndicator, importer);
    }

    private void importChapterFromTo(String sourcePath, String destinationPath, String headerPathIndicator, Importer importer) {
        String raw = new Filesystem().loadFile(sourcePath);
        String content = importer.importTexts(raw);
        String importedText = wrapImportedTextInDestination(headerPathIndicator, content);
        new Filesystem().saveFile(destinationPath, importedText);
    }

    private String wrapImportedTextInDestination(String destinationRelative, String raw) {
        return "["+destinationRelative+"]\n"+raw;
    }
}
