package org.mpasko.japanese.runners.parsers.subtitles;

@FunctionalInterface
public interface Importer {
    String importTexts(String raw);
}
