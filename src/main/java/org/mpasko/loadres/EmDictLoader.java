package org.mpasko.loadres;

import org.mpasko.commons.DictEntry;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.util.Filesystem;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EmDictLoader {

    public static Dictionary loadDictionary() {
        return new EmDictLoader().load();
    }

    public Dictionary load() {
        String content = new Filesystem().loadFile("xml/enamdict.xml", "EUC-JP");
        String[] lines = content.split("\n");
        List<DictEntry> entries = Arrays.stream(lines)
                .filter(line -> !line.contains("ENAMDICT"))
                .filter(line -> line.contains("[") && line.contains("]"))
                .map(this::processLine)
                .collect(Collectors.toList());
        return new Dictionary(entries);
    }

    private DictEntry processLine(String line) {
        String japanese = line.split(" ")[0];
        String reading = inBetween(line, "\\[", "\\]");
        String meaning = inBetween(line, "\\/", "\\/");
        return new DictEntry(japanese, reading, meaning);
    }

    private static String inBetween(String line, String opening, String closing) {
        try {
            return line.split(opening)[1].split(closing)[0];
        } catch (ArrayIndexOutOfBoundsException error) {
            String message = String.format("Unexpected format of line: (expected: '%s' and '%s')\n%s", opening, closing, line);
            throw new RuntimeException(message);
        }
    }
}
