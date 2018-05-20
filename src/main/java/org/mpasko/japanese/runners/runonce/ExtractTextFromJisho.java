/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.runners.runonce;

import java.util.Arrays;
import java.util.List;
import static org.mpasko.japanese.runners.parsers.ParseJishoOutputs.loadRawText;

import org.mpasko.util.Filesystem;

/**
 *
 * @author marcin
 */
public class ExtractTextFromJisho {

    public static void main(String argv[]) {
        processAllSongs();
    }

    public static void processAllSongs() {
        String baseDir = "inputs/songs/";
        //processSongsFrom("other", baseDir + "song_list.txt", baseDir);
        for (String str : Arrays.asList(Filesystem.loadFile(baseDir + "folderlist.txt").split("\n"))) {
            processSongsFrom(str, baseDir);
        }
    }

    private static void processSongsFrom(String album, String basePath) {
        String listfile = basePath + album + "/song_list.txt";
        List<String> songs = Arrays.asList(Filesystem.loadFile(listfile).split("\n"));
        for (String songName : songs) {
            String filename = basePath + album + "/" + songName;
            processSingleSong(album, filename, songName);
        }
    }

    private static void processSingleSong(String album, String filename, String songName) {
        final String rawText = loadRawText(filename);
        final String english = Filesystem.loadFile(filename.replace(".htm", ".txt"));
        Filesystem.saveFile("improved_workflow/texts/songs/" + album + "/" + songName + ".txt", rawText);
        Filesystem.saveFile("improved_workflow/texts/songs/" + album + "/" + songName + ".eng.txt", english);
    }
}
