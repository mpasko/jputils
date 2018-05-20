package org.mpasko.web.songanalysis;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class SongData {
    public String path;
    public String artist;
    public String title;
    public boolean translation = false;

    public SongData(String subdir, String filename, List<String> artists) {
        translation = filename.toLowerCase().contains(".eng.");
        path = subdir+"/"+filename;
        title = cleanup(filename);
        Optional<String> performer = artists.stream()
                .filter((artist) -> matches(subdir, filename, artist))
                .findAny();
        if (performer.isPresent()) {
            artist = cleanup(performer.get());
            String[] parts = artist.split(" ");
            Arrays.stream(parts).forEach(part -> title = title.replaceAll(part, ""));
        } else {
            artist = "unknown";
        }
    }

    private boolean matches(String subdir, String filename, String artist) {
        return cleanup(subdir).contains(cleanup(artist))||cleanup(filename).contains(cleanup(artist));
    }

    public static String cleanup(String input){
        String removedExtensions = input.replaceAll("(\\.txt|\\.eng|\\.htm|\\.mp3)*", "");
        String removedSpecials = removedExtensions.replaceAll("[-&.,!0-9/]*", "");
        String spacefied = removedSpecials.replaceAll("_", " ");
        return spacefied.trim().toLowerCase();
    }
}
