package org.mpasko.japanese.runners.parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.mpasko.util.Filesystem;
import org.mpasko.util.FormatterUtil;

import java.util.stream.Collectors;

public class ParseNovels {

    public static void main(String[] args) {
        new ParseNovels().rewriteAll();
    }

    public void rewriteAll() {
        for (int i=80; i<=120; ++i) {
            rewriteChapter(i);
        }
    }

    private void rewriteChapter(int i) {
        String inputFile = String.format("inputs/novels/tatenoyuusha/%d.html", i);
        String text = loadFile(inputFile);
        String padded = new FormatterUtil().alignString(3,"0", String.valueOf(i));
        String outputFile = String.format("improved_workflow/texts/books/tatenoyuusha/%s.txt", padded);
        new Filesystem().saveFile(outputFile, text);
    }

    private String loadFile(String filename) {
        String content = new Filesystem().loadFile(filename);
        Document doc = Jsoup.parse(content);
        Element novelElement = doc.getElementById("novel_honbun");
        String novelText = novelElement
                .children()
                .stream()
                .map(childElement -> childElement.text())
                .collect(Collectors.joining("\n"));
        return novelText;
    }
}
