package org.mpasko.japanese.runners.parsers;

import java.util.Arrays;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mpasko.util.SimpleUtils;
import org.mpasko.util.StringUtils;
import org.mpasko.util.Util;



public class ParseJlpt
{
    /**
     * Says hello by returning a greeting to the caller.
     *
     * @return a greeting
     */
    public static void main(String args[]) {
        for (String lvl : Arrays.asList("N5", "N4", "2")) {
            processLevel(lvl);
        }
    }

    private static void processLevel(String lvl) {
        String filePattern = "inputs/JLPT Level %s Vocabulary List.htm";
        String entityString = Util.loadFile(String.format(filePattern, lvl));
        Document doc = Jsoup.parse(entityString);
        Element table = doc.getElementsByClass("biglistingTable").first();
        Elements rows = table.getElementsByTag("tr");
        StringBuilder all = new StringBuilder();
        StringBuilder withoutKanji = new StringBuilder();
        for (Element row : rows) {
            //System.out.println(row.html());
            if (row.getElementsByTag("th").size() > 0) {
                continue;
            }
            String hiragana = StringUtils.clear(row.getElementsByClass("kanji").first().text());
            Elements kanjiElements = row.getElementsByClass("kanji");
            //System.out.println(kanjiElements.first().html());
            String kanji = StringUtils.clear(kanjiElements.get(1).text());
            String english = StringUtils.clear(row.getElementsByClass("eng").first().text());
            if (kanji.isEmpty()) {
                withoutKanji.append(hiragana);
                withoutKanji.append(" -");
                withoutKanji.append(english);
                withoutKanji.append("\n");
            } else {
                all.append(kanji);
                all.append(" ");
                all.append(hiragana);
                all.append(" -");
                all.append(english);
                all.append("\n");
            }
        }
        Util.saveFile(String.format("dictionaries/jlpt%s.txt", lvl), all.toString());
        Util.saveFile(String.format("dictionaries/jlpt%s_withoutKanji.txt", lvl), withoutKanji.toString());
    }
}

