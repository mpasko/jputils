package org.mpasko.editor;

import org.mpasko.management.console.DefaultConfig;
import org.mpasko.util.Filesystem;
import org.mpasko.util.IFilesystem;
import org.mpasko.util.StringUtils;

public class Asset {
    public String english;
    public String japanese;
    public String name;
    public String path;
    public String directory;

    public static Asset load(String filename) {
        Asset asset = new Asset();
        asset.japanese = new Filesystem().loadFile(filename);
        asset.english = new Filesystem().tryLoadFile(filename.replaceAll(".txt", ".eng.txt"));
        asset.path = filename;
        asset.name = StringUtils.lastSegment(filename, "/");
        asset.directory = filename.replaceAll(asset.name, "");
        return asset;
    }

    public static void save(String path, String name, Asset asset) {
        save(StringUtils.joinPath(path, name), asset);
    }

    public static void save(String name, Asset asset) {
        String name_cut = name.replaceAll(".txt", "");
        String realignedPath = StringUtils.alignPaths(DefaultConfig.sources, name_cut);
        IFilesystem fs = new Filesystem();
        saveJapanese(fs, realignedPath, asset);
        saveEnglish(fs, realignedPath, asset);
    }

    private static void saveEnglish(IFilesystem fs, String realignedPath, Asset asset) {
        String english_filename = StringUtils.joinPath(realignedPath+".eng.txt");
        fs.saveFile(english_filename, asset.english);
    }

    private static void saveJapanese(IFilesystem fs, String realignedPath, Asset asset) {
        String japanese_filename = StringUtils.joinPath(realignedPath+".txt");
        fs.saveFile(japanese_filename, asset.japanese);
    }
}
