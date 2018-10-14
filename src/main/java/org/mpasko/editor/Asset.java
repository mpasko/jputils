package org.mpasko.editor;

import org.mpasko.util.Filesystem;
import org.mpasko.util.StringUtils;

public class Asset {
    public String english;
    public String japanese;
    public String name;

    public static Asset load(String filename) {
        Asset asset = new Asset();
        asset.japanese = new Filesystem().loadFile(filename);
        asset.english = new Filesystem().tryLoadFile(filename.replaceAll(".txt", ".eng.txt"));
        asset.name = StringUtils.lastSegment(filename, "/");
        return asset;
    }
}
