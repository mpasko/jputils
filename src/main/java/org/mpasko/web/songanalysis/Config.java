package org.mpasko.web.songanalysis;

import org.mpasko.console.DefaultConfig;

import java.util.Arrays;
import java.util.List;

public class Config {
    public List<String> artists = Arrays.asList("hiroyuki_sawano",
            "nano",
            "garnidelia",
            "yousei_teikoku",
            "kalafina",
            "kenshi_yonezu",
            "aimer",
            "egoist",
            "cream",
            "stereopony",
            "orange range",
            "yui",
            "eir aoi",
            "wagakki band",
            "band maid",
            "lisa",
            "luna haruna",
            "haruka tomatsu",
            "ling tosite sigure");
    public String lyrics_path = "./improved_workflow/texts/songs";
    public String audio_path = "/mnt/data/repo/Music/nihongo";
    public List<String> english_songs = Arrays.asList("unhappy refrain", "scarlet story", "the beast",
            "go to west", "silver sky", "complicated", "neophobia", "nightcore silence",
            "guilty crown bios", "layers mod", "owari no seraph",
            "god of ink", " keep on keping", "perfect time", "release my soul", "blessing departures", "breathless",
            "out of control", "through my blood", "glowenglish version", "last refrain");
}
