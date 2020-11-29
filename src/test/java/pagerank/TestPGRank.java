//package pagerank;
//
//import org.junit.Test;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//
//import static com.cpp.pagerank.PageRank.calculatePageRank;
//import static com.cpp.pagerank.Utils.generateTwitterProfileSamples;
//
///**
// * Created by IntelliJ IDEA.
// * User: jayavardhanpatil
// * Date: 11/29/20
// * Time:  11:46
// */
//
//public class TestPGRank {
//
//
//    @Test
//    public void test_PageRank() throws IOException {
//
//        double[][] pageMatrix = {{0, 0, 0, 0, 1}, {0.5, 0, 0, 0, 0}, {0.5, 0, 0, 0, 0},
//                {0, 1, 0.5, 0, 0}, {0, 0, 0.5, 1, 0}};
//        ArrayList<HashMap<Integer, Double>> listOfEdges = new ArrayList<>();
//
//        HashMap<Integer, Double> map = new HashMap<>();
//        map.put(1, 1.0);
//        map.put(2, 1.0 / 8);
//        map.put(3, 1.0 / 3);
//        map.put(4, 1.0 / 3);
//        map.put(5, 1.0 / 2);
//        map.put(6, 1.0);
//        map.put(7, 1.0);
//        listOfEdges.add(map);
//        map = new HashMap<>();
//        map.put(2, 1.0 / 8);
//        listOfEdges.add(map);
//        map = new HashMap<>();
//        map.put(2, 1.0 / 8);
//        map.put(3, 1.0 / 3);
//        listOfEdges.add(map);
//        map = new HashMap<>();
//        map.put(2, 1.0 / 8);
//        listOfEdges.add(map);
//        map = new HashMap<>();
//        map.put(2, 1.0 / 8);
//        listOfEdges.add(map);
//        map = new HashMap<>();
//        map.put(2, 1.0 / 8);
//        map.put(4, 1.0 / 3);
//        listOfEdges.add(map);
//        map = new HashMap<>();
//        map.put(2, 1.0 / 8);
//        map.put(3, 1.0 / 3);
//        map.put(4, 1.0 / 3);
//        map.put(5, 1.0 / 2);
//        listOfEdges.add(map);
//        map = new HashMap<>();
//        map.put(0, 1.0);
//        map.put(2, 1.0 / 8);
//        listOfEdges.add(map);
//        double[] pageRank = new double[8];
//        Arrays.fill(pageRank, (1.0) / pageRank.length);
//        double[] currentPageRank = Arrays.copyOf(pageRank, pageRank.length);
//        calculatePageRank(pageRank, currentPageRank, listOfEdges);
//    }
//
//}