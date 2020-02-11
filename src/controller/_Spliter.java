package controller;

import java.util.ArrayList;

public class _Spliter {
    public static ArrayList<String> getSplit(String str){
        int size = 130;
        ArrayList<String> arraylist = new ArrayList<String>();
        for(int start = 0; start < str.length(); start += size) {
            arraylist.add(str.substring(start, Math.min(str.length(), start+size)));
        }
        return arraylist;
    }
}
