package com.cpp.pagerank;

import java.io.IOException;

import static com.cpp.pagerank.PageRank.pageRankCalculate;
import static com.cpp.pagerank.Utils.generateTwitterProfileSamples;

/**
 * Created by IntelliJ IDEA.
 * User: jayavardhanpatil
 * Date: 11/29/20
 * Time:  12:00
 */

public class PageRankTestHugeData {

    public static void main(String[] args) {

        if(args[0] != null){
            generateTwitterProfileSamples(Integer.parseInt(args[0]));
            try {
                pageRankCalculate();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Done");

    }

}