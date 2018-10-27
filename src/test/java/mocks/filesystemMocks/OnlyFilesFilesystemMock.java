package mocks.filesystemMocks;

import org.mpasko.util.IFilesystem;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class OnlyFilesFilesystemMock implements IFilesystem {
    @Override
    public String loadFilesInDirectory(String stringPath) {
        return null;
    }

    @Override
    public List<String> getSubdirectories(String path) {
        return new LinkedList<>();
    }

    @Override
    public List<String> getSubfiles(String basePath) {
        return Arrays.asList("file1", "file2");
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

    @Override
    public boolean isFile(String path) {
        return false;
    }

    @Override
    public boolean isDirectory(String path) {
        return true;
    }
}
