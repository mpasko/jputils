package org.mpasko.web.songanalysis;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Classification {
    public String artist;
    public List<SongData> lyrics = new LinkedList<>();
    public List<SongData> audio = new LinkedList<>();

    public Classification(String artist) {
        this.artist = artist;
    }

    public void putDistinctLyrics(SongData lyric) {
        putDistinct(lyrics, lyric);
    }

    public void putDistinctAudio(SongData audio_song) {
        putDistinct(audio, audio_song);
    }

    private static void putDistinct(List<SongData> collection, SongData new_entry){
        Optional<SongData> found =  collection.stream()
            .filter(existing -> existing.title.equals(new_entry.title))
            .findAny();
        if (found.isPresent()) {
            found.get().translation = true;
        } else {
            collection.add(new_entry);
        }
    }
}
