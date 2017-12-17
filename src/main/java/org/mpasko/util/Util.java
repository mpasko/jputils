/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marcin
 */
public class Util {

    public static void isNull(Object obj, String name) {
        if (obj == null) {
            System.out.println(name + " is null!");
        }
    }

    public static <T> List<T> singleElemList(T item) {
        List<T> list = new LinkedList<T>();
        list.add(item);
        return list;
    }

    public static List<String> strings(String... elem) {
        List<String> elems1 = new LinkedList<String>();
        elems1.addAll(Arrays.asList(elem));
        return elems1;
    }

    public static String stringifyList(List<String> kunomi) {
        StringBuilder b = new StringBuilder();
        for (String kun : kunomi) {
            if (!kun.equals(kunomi.get(0))) {
                b.append(",");
            }
            b.append(kun);
        }
        return b.toString();
    }

//    public static void collectionIntoArray(Iterable<CellValue> mesh_cells, List<CellValue> array) {
//        Iterator<CellValue> iter = mesh_cells.iterator();
//        while (iter.hasNext()) {
//            CellValue val = iter.next();
//            array.add(val);
//        }
//    }
    public static String streamToString(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            out.append(line).append("\n");
        }
        return out.toString();
    }

    public static void stringToStream(String in, OutputStream out) throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
        for (String line : in.split("\n")) {
            bw.write(line);
            bw.newLine();
        }
        bw.close();
    }

    public static void binaryToStream(String content, OutputStream fos) {
        try {
            fos.write(content.getBytes());
        } catch (IOException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
    }

    public static String negatedComparator(final String comparator) {
        String neg;
        if (comparator.equals("<")) {
            neg = ">=";
        } else if (comparator.equals(">")) {
            neg = "<=";
        } else if (comparator.equals(">=")) {
            neg = "<";
        } else if (comparator.equals("<=")) {
            neg = ">";
        } else if (comparator.equals("!=") || comparator.equals("<>")) {
            neg = "=";
        } else {
            neg = "<>";
        }
        return neg;
    }

//    public static Input JsonToInput(String json) {
//        try {
//            //System.out.println(item.toString());
//            ObjectMapper mapper = new ObjectMapper();
//            mapper.enable(SerializationConfig.Feature.INDENT_OUTPUT);
//            return mapper.readValue(json, Input.class);
//        } catch (Throwable ex) {
//            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
//            throw new RuntimeException(ex);
//        }
//        //return "";
//    }
//    public static String objectToJson(Object item) {
//        try {
//            //System.out.println(item.toString());
//            ObjectMapper mapper = new ObjectMapper();
//            mapper.enable(SerializationConfig.Feature.INDENT_OUTPUT);
//            return mapper.writeValueAsString(item);
//        } catch (Throwable ex) {
//            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
//            throw new RuntimeException(ex);
//        }
//        //return "";
//    }
    public static String loadFilesInDirectory(String stringPath) {
        StringBuilder builder = new StringBuilder();
        File[] files = new File(stringPath).listFiles();
        Arrays.stream(files)
                .map(path -> path.toPath())
                .filter(Files::isRegularFile)
                .forEach(content
                        -> builder.append(loadFile(content.toAbsolutePath().toString()))
                        .append("\n"));
        return builder.toString();
    }

    public static String loadFile(String filename) {
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(filename);
            return Util.streamToString(stream);
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

    public static Map<String, Object> lowercaseKeys(Map<String, Object> event) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        for (Entry<String, Object> entry : event.entrySet()) {
            //result.put(entry.getKey(), entry.getValue());
            result.put(entry.getKey().toLowerCase(Locale.getDefault()), entry.getValue());
        }
        return result;
    }

    public static void saveFile(String full_name, String content) {
        FileOutputStream fos = null;
        try {
            File file = new File(full_name);
            file.getParentFile().mkdirs();
            fos = new FileOutputStream(file);
            fos.flush();
            Util.stringToStream(content, fos);
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

    public static String formatEventByKeyParams(List<String> keys, Map<String, Object> event) {
        StringBuilder result = new StringBuilder();
        result.append("{");
        int index = 0;
        for (String key : keys) {
            result.append(key).append(": ");
            result.append(event.get(key));
            if (index < keys.size() - 1) {
                result.append(", ");
            }
        }
        result.append("}");
        String string = result.toString();
        return string;
    }

    public static String formatEventByKeyParams(Map<String, Object> event) {
        return formatEventByKeyParams(new ArrayList<String>(event.keySet()), event);
    }
}
