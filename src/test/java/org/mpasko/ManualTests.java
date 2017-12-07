/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko;

import java.util.ArrayList;
import java.util.Arrays;
import org.mpasko.util.Randomation;

/**
 *
 * @author marcin
 */
public class ManualTests {
    public static void main(String args[]) {
        for (int i = 0; i<50; ++i) {
            ArrayList<Integer> list = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6));
            System.out.println(Randomation.choose(list));
            Randomation.shuffle(list);
            System.out.println(list);
        }
    }
}
