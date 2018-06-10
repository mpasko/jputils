package org.mpasko.util;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Filesystem implements IFilesystem {

    public String streamToString(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            out.append(line).append("\n");
        }
        return out.toString();
    }

    public void stringToStream(String in, OutputStream out) throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
        for (String line : in.split("\n")) {
            bw.write(line);
            bw.newLine();
        }
        bw.close();
    }

    private List<String> getSubitems(String path, final FileFilter filter) throws RuntimeException {
        File[] directories = new File(path).listFiles(filter);
        if (directories == null) {
            throw new RuntimeException("Invalid path: " + path);
        }
        List<String> list = Arrays.stream(directories)
            .map(dir -> dir.getName())
            .collect(Collectors.toList());
        return list;
    }

    private void createPathIfNotExists(File file) {
        final File parentFile = file.getParentFile();
        if (parentFile!= null && !parentFile.exists()) {
            createPathIfNotExists(parentFile);
            parentFile.mkdirs();
        }
    }


    @Override
    public String loadFilesInDirectory(String stringPath) {
        StringBuilder builder = new StringBuilder();
        File[] files = new File(stringPath).listFiles();
        if(files == null) {
            return "";
        }
        Arrays.stream(files)
                .map(path -> path.toPath())
                .filter(Files::isRegularFile)
                .forEach(content
                        -> builder.append(loadFile(content.toAbsolutePath().toString()))
                        .append("\n"));
        return builder.toString();
    }

    @Override
    public List<String> getSubdirectories(String path) {
        return getSubitems(path, File::isDirectory);
    }

    @Override
    public List<String> getSubfiles(String basePath) {
        return getSubitems(basePath, File::isFile);
    }

    @Override
    public String loadFile(String filename) {
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(filename);
            return streamToString(stream);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public String tryLoadFile(String filename) {
        try {
            return loadFile(filename);
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public void saveFile(String full_name, String content) {
        //String full_name = full_path.replaceAll("\\/", "\\");
        FileOutputStream fos = null;
        try {
            File file = new File(full_name);
            createPathIfNotExists(file);
            fos = new FileOutputStream(file);
            fos.flush();
            stringToStream(content, fos);
        } catch (IOException ex) {
            throw new RuntimeException("Error saving file: " + full_name, ex);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException ex) {
                    throw new RuntimeException("Error closing file stream: " + full_name, ex);
                }
            }
        }
    }
}
