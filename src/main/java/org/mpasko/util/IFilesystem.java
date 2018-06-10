package org.mpasko.util;

import java.util.List;

public interface IFilesystem {
    String loadFilesInDirectory(String stringPath);

    List<String> getSubdirectories(String path);

    List<String> getSubfiles(String basePath);

    String loadFile(String filename);

    String tryLoadFile(String filename);

    void saveFile(String full_name, String content);
}
