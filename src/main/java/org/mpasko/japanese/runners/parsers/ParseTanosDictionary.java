package org.mpasko.japanese.runners.parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mpasko.util.*;

public class ParseTanosDictionary {

    /**
     * Says hello by returning a greeting to the caller.
     *
     * @return a greeting
     */
    public static void main(String args[]) {
        processfile();
    }

    private static void processfile() {
        String filename = "inputs/JLPT_N2_tanos.htm";
        String entityString = new Filesystem().loadFile(filename);
        Document doc = Jsoup.parse(entityString);
        Element table = doc.getElementsByTag("table").get(1);
        Elements rows = table.getElementsByTag("tr");
        StringBuilder all = new StringBuilder();
        StringBuilder withoutKanji = new StringBuilder();
        for (Element row : rows) {
            Elements cols = row.getElementsByTag("td");
            if (cols.size() > 0) {
                String kana = StringUtils.clear(cols.get(0).text());
                //System.out.println(kanjiElements.first().html());
                System.out.println(kana);
                String romaiji = StringUtils.clear(cols.get(1).text());
                String english = StringUtils.clear(cols.get(2).text());
                if (kana.trim().isEmpty()) {
                    withoutKanji.append(kana);
                    withoutKanji.append(" -");
                    withoutKanji.append(english);
                    withoutKanji.append("\n");
                } else {
                    all.append(kana);
                    all.append(" ");
                    all.append(romaiji);
                    all.append(" -");
                    all.append(english);
                    all.append("\n");
                }
            }
        }
        new Filesystem().saveFile("dictionaries/n2_tanos.txt", all.toString());
        new Filesystem().saveFile("dictionaries/n2_tanos_withoutKanji.txt", withoutKanji.toString());
    }
}
