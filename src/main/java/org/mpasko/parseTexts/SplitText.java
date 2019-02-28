package org.mpasko.parseTexts;

import java.util.LinkedList;
import java.util.List;

public class SplitText {
    private final int MAX_CHUNK_SIZE;
    private final int MIN_CHUNK_SIZE;

    public SplitText(int maxChunkSize, int minChunkSize) {
        MAX_CHUNK_SIZE = maxChunkSize;
        MIN_CHUNK_SIZE = minChunkSize;
    }

    public SplitText() {
        this(500, 200);
    }

    public List<String> splitEvenly(String songText) {
        final LinkedList<String> result = new LinkedList<>();
        String[] split = songText.split("\n|。|、|「|」");
        StringBuilder currentChunk = new StringBuilder();
        for (int index = 0; index < split.length; ++index) {
            currentChunk.append(split[index]).append("\n");
            if (currentChunk.length() > calculateChunkSize(songText)) {
                result.add(currentChunk.toString());
                currentChunk = new StringBuilder();
            }
        }
        result.add(currentChunk.toString());
        return result;
    }

    private int calculateChunkSize(String songText) {
        int total = songText.length();
        if (total < MAX_CHUNK_SIZE) {
            return total;
        } else {
            int chunksNumber = total / MIN_CHUNK_SIZE;
            return total / chunksNumber;
        }
    }
}
