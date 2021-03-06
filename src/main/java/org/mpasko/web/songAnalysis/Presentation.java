package org.mpasko.web.songAnalysis;

import java.util.List;

public class Presentation {

    private List<SongData> getLyrics() {
        Config conf = Config.loadConfig();
        return new Engine().identifySongs(conf.lyrics_path, conf.artists, conf.english_songs);
    }

    private List<SongData> getAudio() {
        Config conf = Config.loadConfig();
        return new Engine().identifySongs(conf.audio_path, conf.artists, conf.english_songs);
    }

    public List<Classification> getClassifications() {
        return new Engine().classify(this.getLyrics(), this.getAudio());
    }
}
