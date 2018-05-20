package romanlanguages;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mpasko.util.*;


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
        String filePattern = "inputs/www.goethe-verlag.com/book2/_VOCAB/IT/ITEN/%s.HTM";
        String filename = String.format(filePattern, alignedNumber);
        String entityString = Filesystem.loadFile(filename);
        Document doc = Jsoup.parse(entityString);
        Element table = doc.getElementsByTag("table").get(1);
        Elements rows = table.getElementsByTag("td");
        StringBuilder dictionary = new StringBuilder();
        for (Element row : rows) {
            String word = StringUtils.clear(row.getElementsByClass("Stil46").first().text());
            //System.out.println(kanjiElements.first().html());
            String translation = StringUtils.clear(row.getElementsByClass("Stil36").first().text());
            dictionary.append(translation);
            dictionary.append("-");
            dictionary.append(word);
            dictionary.append("\n");
            System.out.println(word);
        }
        Filesystem.saveFile(String.format("dictionaries/goethe%s_italiano.txt", alignedNumber), dictionary.toString());
    }
}

