package org.mpasko.web.songAnalysis;

import org.mpasko.util.Filesystem;

import java.util.*;

public class Engine {
    public List<SongData> identifySongs(String root_path, List<String> artists, List<String> english_blacklist) {
        LinkedList<SongData> songDatas = new LinkedList<>();
        List<String> subdirs = new Filesystem().getSubdirectories(root_path);
        for(String subdir : subdirs) {
            new Filesystem().getSubfiles(root_path+"/"+subdir)
                    .stream()
                    .filter(file -> isNotEnglish(file, english_blacklist))
                    .forEach(file -> songDatas.add(new SongData(subdir, file, artists)));
        }
        return songDatas;
    }

    private boolean isNotEnglish(String file_name, List<String> english_blacklist) {
        return !english_blacklist.stream()
                .anyMatch(english_song -> SongData.cleanup(file_name).contains(english_song));
    }

    public List<Classification> classify(List<SongData> lyrics, List<SongData> audios) {
        HashMap<String, Classification> map = new HashMap<>();
        lyrics.forEach(lyric -> {
            if (!map.containsKey(lyric.artist)) {
                map.put(lyric.artist, new Classification(lyric.artist));
            }
            map.get(lyric.artist).putDistinctLyrics(lyric);
        });
        audios.forEach(audio -> {
            if (!map.containsKey(audio.artist)){
                map.put(audio.artist, new Classification(audio.artist));
            }
            map.get(audio.artist).putDistinctAudio(audio);
        });
        return new LinkedList(map.values());
    }
}
