package au.com.d2dcrc.yago2es;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.*;
public class rdf_on {

    public static ArrayList<ArrayList<Integer>> ls = new ArrayList<>();

    public static int[] arr = new int[]{1,2,3,4};
    public static void main(String[] args) {
        ArrayList<Integer> tmp = new ArrayList<>();
        int depth = 3;
        for (int i = 0; i < depth; i++) {
            tmp.add(-1);
        }


        backTrack(tmp, 0,depth);
        for (ArrayList<Integer> l : ls) {
            System.out.println(l);
        }
    }

    public static void backTrack(ArrayList<Integer> tmp, int level, int depth){


        if (tmp.get(depth-1) > -1){
            for (int i = 1; i < depth; i++) {
                if (tmp.get(i) < tmp.get(i-1)){
                    return;
                }
            }
            ls.add(new ArrayList<>(tmp));
            return;
        }

        for (int i = 0; i < 4; i++) {
            if (tmp.contains(new Integer(arr[i]))){
                continue;
            }
            tmp.set(level, arr[i]);
            backTrack(new ArrayList<>(tmp), ++level, depth);
            level--;
        }
    }



 }

