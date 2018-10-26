package org.mpasko.parseTexts.stemmers;

public class InfixStemmer implements IStemmer {

    private final String prefixes;
    private final String postfixes;

    public InfixStemmer(String prexixes, String postfixes) {
        this.prefixes = prexixes;
        this.postfixes = postfixes;
    }

    @Override
    public String stem(String word) {
        return removeAllCharsFrom(word, prefixes + postfixes);
    }

    private String removeAllCharsFrom(String word, String chars) {
        String worked = word;
        for (char character : chars.toCharArray()) {
            worked = worked.replaceAll(new String(new char[]{character}), "");
        }
        return worked;
    }
}
