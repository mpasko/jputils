package romanlanguages;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
        Filesystem.saveFile("texts/goethe_italiano.txt", all.toString());
    }

    private static StringBuilder processfile(String alignedNumber) {
        String filePattern = "inputs/www.goethe-verlag.com/book2/IT/ITEN/ITEN%s.HTM";
        String filename = String.format(filePattern, alignedNumber);
        String entityString = Filesystem.loadFile(filename);
        Document doc = Jsoup.parse(entityString);
        StringBuilder all = new StringBuilder();
        Elements elements = doc.getElementsByClass("Stil35");
        for (Element elem : elements) {
            String text = elem.text();
            if (text.length()<500) {
                String processed = text.replaceAll("&#", "\\u");
                all.append(processed).append("\n");
            }
        }
        return all;
    }
}

