package chinese;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.mpasko.util.Filesystem;
import org.mpasko.util.FormatterUtil;


public class ParseGoetheSentences
{
    /**
     * Says hello by returning a greeting to the caller.
     *
     * @return a greeting
     */
    public static void main(String args[]) {
        StringBuilder all = new StringBuilder();
        for (int i = 1; i<=100; ++i) {
            all.append(i).append("\n");
            String alignedNumber = FormatterUtil.alignString(3, "0", String.valueOf(i+2));
            all.append(processfile(alignedNumber));
        }
        Filesystem.saveFile("texts/goethe_chineze_only_hanzi.txt", all.toString());
    }

    private static StringBuilder processfile(String alignedNumber) {
        String filePattern = "inputs/www.goethe-verlag.com/book2/IT/ITZH/ITZH%s.HTM";
        String filename = String.format(filePattern, alignedNumber);
        String entityString = Filesystem.loadFile(filename);
        Document doc = Jsoup.parse(entityString);
        StringBuilder all = new StringBuilder();
        for (int num=0; num<40; ++num) {
            Element div = doc.getElementById(String.format("hn_%s", num));
            if (div != null) {
                Element link = div.getElementsByTag("a").first();
                String kanji_romaji = link.text();
                String romaji = link.getElementsByTag("span").first().text();
                String kanji = kanji_romaji.replace(romaji, "");
                String processed = kanji.replaceAll("&#", "\\u");
                all.append(processed).append("\n");
            }
        }
        return all;
    }
}

