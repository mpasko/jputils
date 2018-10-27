package mocks.filesystemMocks;

import org.mpasko.util.IFilesystem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class NestedFilesystem implements IFilesystem {

    public Map<String, IFilesystem> subdirectoryMocks = new TreeMap<>();

    public void nest(String base, IFilesystem child) {
        subdirectoryMocks.put(base, child);
        child.getSubdirectories(base)
                .stream()
                .forEach(subdirs -> subdirectoryMocks.put(base+"/"+subdirs, child));
        child.getSubfiles(base)
                .stream()
                .forEach(subfiles -> subdirectoryMocks.put(base+"/"+subfiles, child));
    }

    @Override
    public String loadFilesInDirectory(String stringPath) {
        return subdirectoryMocks.get(stringPath).loadFilesInDirectory(stringPath);
    }

    @Override
    public List<String> getSubdirectories(String path) {
        List<String> result = subdirectoryMocks.get(trimLastSlash(path)).getSubdirectories(path);
        //System.out.println("query:"+path+" subdirs:"+result.toString());
        return result;
    }

    @Override
    public List<String> getSubfiles(String basePath) {
        String safePath = trimLastSlash(basePath);
        List<String> result = subdirectoryMocks.get(safePath).getSubfiles(basePath);
        //System.out.println("query:"+basePath+" files:"+result.toString());
        return result;
    }

    private static String trimLastSlash(String path) {
        String safePath = path;
        if (path.endsWith("/")) {
            safePath = safePath.substring(0,safePath.length()-1);
        }
        return safePath;
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
