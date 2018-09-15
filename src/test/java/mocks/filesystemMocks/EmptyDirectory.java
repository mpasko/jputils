package mocks.filesystemMocks;

import org.mpasko.util.IFilesystem;

import java.util.LinkedList;
import java.util.List;

public class EmptyDirectory implements IFilesystem {
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
