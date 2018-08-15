package mocks.filesystemMocks;

import org.mpasko.util.IFilesystem;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class OnlyFoldersFilesystemMock implements IFilesystem {
    private final String base;

    public OnlyFoldersFilesystemMock(String base){
        this.base = base;
    }

    @Override
    public String loadFilesInDirectory(String stringPath) {
        return null;
    }

    @Override
    public List<String> getSubdirectories(String path) {
        if (path.length() > base.length()+1) {
            return new LinkedList<>();
        }
        return Arrays.asList("dir1", "dir2");
    }

    @Override
    public List<String> getSubfiles(String basePath) {
        return new LinkedList<>();
    }

    @Override
    public String loadFile(String filename) {
        return null;
    }

    @Override
    public String tryLoadFile(String filename) {
        return null;
    }

    @Override
    public void saveFile(String full_name, String content) {

    }
}
