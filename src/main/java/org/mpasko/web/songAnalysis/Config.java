package org.mpasko.web.songAnalysis;

import org.mpasko.util.Filesystem;

import java.util.Arrays;
import java.util.List;

public class Config {
    private Config() {

    }

    public static Config loadConfig() {
        Config config = new Config();
        config.artists = loadList("./improved_workflow/config/artists.txt");
        config.english_songs = loadList("./improved_workflow/config/ignore_songs.txt");
        return config;
    }

    private static List<String> loadList(String filename) {
        String raw = new Filesystem().loadFile(filename);
        return Arrays.asList(raw.split("\n"));
    }

    public List<String> artists;
    public String lyrics_path = "./improved_workflow/texts/songs";
    public String audio_path = "/mnt/data/repo/Music/nihongo";
    public List<String> english_songs;
}
