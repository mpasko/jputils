package org.mpasko.japanese.runners.parsers;

import java.util.Arrays;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mpasko.commons.Classifier;
import org.mpasko.util.FormatterUtil;
import org.mpasko.util.SimpleUtils;
import org.mpasko.util.StringUtils;
import org.mpasko.util.Util;



public class ParseGoetheDictionary
{
    /**
     * Says hello by returning a greeting to the caller.
     *
     * @return a greeting
     */
    public static void main(String args[]) {
        for (int i = 1; i<=42; ++i) {
            String alignedNumber = FormatterUtil.alignString(2, "0", String.valueOf(i));
            processfile(alignedNumber);
        }
    }

    private static void processfile(String alignedNumber) {
        String filePattern = "inputs/www.goethe-verlag.com/book2/_VOCAB/EN/ENJA/%s.HTM";
        String filename = String.format(filePattern, alignedNumber);
        String entityString = Util.loadFile(filename);
        Document doc = Jsoup.parse(entityString);
        Element table = doc.getElementsByTag("table").get(1);
        Elements rows = table.getElementsByTag("td");
        StringBuilder all = new StringBuilder();
        StringBuilder withoutKanji = new StringBuilder();
        for (Element row : rows) {
            String kana = StringUtils.clear(row.getElementsByClass("Stil46").first().text());
            //System.out.println(kanjiElements.first().html());
            String romaiji = StringUtils.clear(row.getElementsByClass("Stil39").first().text());
            String english = StringUtils.clear(row.getElementsByClass("Stil36").first().text());
            if (!Classifier.classify(kana).containsKanji()) {
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
            System.out.println(kana);
        }
        Util.saveFile(String.format("dictionaries/goethe%s.txt", alignedNumber), all.toString());
        Util.saveFile(String.format("dictionaries/goethe%s_withoutKanji.txt", alignedNumber), withoutKanji.toString());
    }
}

