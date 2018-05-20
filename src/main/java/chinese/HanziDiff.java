/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chinese;

import org.mpasko.util.Filesystem;

/**
 *
 * @author marcin
 */
public class HanziDiff {
    public static void main(String[] args) {
        String file = Filesystem.loadFile("inputs/zh_differences.txt");
        for (String line : file.split("\n")) {
            String[] chars = line.split("-");
            String jp = chars[0];
            String zh = chars[2];
            if (!jp.equals(zh)) {
                System.out.println(String.format("%s -> %s", jp, zh));
            }
        }
    }
}
