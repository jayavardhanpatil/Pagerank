package com.cpp.pagerank;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: jayavardhanpatil
 * Date: 11/19/20
 * Time:  17:27
 */

public class PageRank {

    private static HashMap<Integer, TwitterProfile> profileHashMap = new HashMap<>();
    private static HashMap<Integer, TwitterProfile> indexedprofileHashMap = new HashMap<>();
    static TreeMap<Double, TwitterProfile> rankedProfiles = new TreeMap<>(Collections.reverseOrder());

    private static long totalNodes = 0;
    private static boolean istest = false;
    public static void main(String[] args) throws IOException {

        totalNodes = Long.parseLong(args[0]);

        if(args[1].equalsIgnoreCase("test")){
            istest = true;
            test();
        }else {

            try {
                generateTwitterProfileSamples();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Sample profiles");

            int totalVertices = profileHashMap.size();

          //  double[][] pageMatrix = new double[totalVertices][totalVertices];
            double[] pageRank = new double[totalVertices];

            ArrayList<HashMap<Integer, Double>> pageMat = new ArrayList<>();

            //initialize the first
            double initializethePageRank = (double) (1.0 / totalVertices);
            Arrays.fill(pageRank, initializethePageRank);

            double[] currentPageRank = Arrays.copyOf(pageRank, pageRank.length);

            //fill the matrix
            for (int j = 0; j < totalVertices; j++) {
                ArrayList<Integer> followers = (ArrayList<Integer>) indexedprofileHashMap.get(j).getFollowersList();
                HashMap<Integer, Double> listOfFollowers = new HashMap<>();
                int followersCount = indexedprofileHashMap.get(j).getFollowers();
                for (int twitterId : followers) {
                    int getProfileIdex = profileHashMap.get(twitterId).getIndex();
                    listOfFollowers.put(getProfileIdex, (double) (1.0 / followersCount));
                   // pageMatrix[j][getProfileIdex] = (double) 1.0 / followersCount;
                }
                pageMat.add(listOfFollowers);
            }
                        //calculatePageRank(pageMatrix, pageRank, currentPageRank, pageMat);

            LocalDateTime startTime = LocalDateTime.now();
            calculatePageRank(pageRank, currentPageRank, pageMat);
            LocalDateTime endTime = LocalDateTime.now();
            System.out.println(startTime.until(endTime, ChronoUnit.SECONDS) + " Seconds");
            //System.out.println((endTime.getNano() - startTime.getNano()));
        }
    }


    public static void test(){

        double[][] pageMatrix = {{0,0,0,0,1},{0.5, 0, 0, 0, 0},{0.5, 0, 0, 0, 0},
                {0, 1, 0.5, 0, 0}, {0, 0, 0.5, 1, 0}};
        double[] pageRank = new double[5];
        double[] currentPageRank = Arrays.copyOf(pageRank, pageRank.length);

        //calculatePageRank(pageMatrix, pageRank, currentPageRank);
    }

    public static void calculatePageRank(double[]pageRank, double[] currentPageRank, ArrayList<HashMap<Integer, Double>> pageMat){
        boolean isChangeInPageRankVal = true;
        int iterations = 0;

        //double[] previousPageRank = Arrays.copyOf(pageRank, pageRank.length);

        while(isChangeInPageRankVal && iterations < 500){
            isChangeInPageRankVal = false;
            for(int k=0;k<pageMat.size();k++){
                double current_page_rank = 0.0;
                //double current_page_rank1 = 0.0;
                if(!istest) {

                    for (int followers : indexedprofileHashMap.get(k).getFollowersList()) {
                        int getProfileIdex = profileHashMap.get(followers).getIndex();
                        //current_page_rank += pageMatrix[k][getProfileIdex] * pageRank[getProfileIdex];
                        current_page_rank += pageMat.get(k).get(getProfileIdex) * pageRank[getProfileIdex];
                    }

//                    for(int followers : indexedprofileHashMap.get(k).getFollowersList()){
//                        int getProfileIdex = profileHashMap.get(followers).getIndex();
//
//                    }
                    //System.out.println(BigDecimal.valueOf(current_page_rank).toPlainString());

                }else {
//                    for (int j = 0; j < pageMatrix.length; j++) {
//                        current_page_rank += pageMatrix[k][j] * pageRank[j];
//                    }
                }

                current_page_rank = ((0.15) / pageRank.length) + (0.85 * current_page_rank);
                if(!isChangeInPageRankVal && Math.abs(currentPageRank[k] - current_page_rank) >= 0.000000000001){
                    isChangeInPageRankVal = true;
                }

                currentPageRank[k] = current_page_rank;
            }
            pageRank = currentPageRank;
            iterations++;
        }
        System.out.println("Total Iterations : "+iterations);

        for (int i=0;i<currentPageRank.length;i++){
            //System.out.println(currentPageRank[i]);
            rankedProfiles.put(currentPageRank[i], indexedprofileHashMap.get(i));
        }

        int count = 0;
        for(Map.Entry<Double, TwitterProfile> data : rankedProfiles.entrySet()){
            System.out.println(BigDecimal.valueOf(data.getKey()).toPlainString());
            System.out.println(data.getValue());
            count++;
            if(count > 10) break;
        }
    }

    public static void generateTwitterProfileSamples() throws IOException {
        Random random = new Random();

        ArrayList<Integer> profileIds = new ArrayList<>();
        for(int i = 0;i<totalNodes;i++){
            int randomNumber = 1000000 + random.nextInt(9000000);
            if (profileIds.contains(randomNumber)) continue;
            else profileIds.add(randomNumber);

        }

        for(int i =0;i<profileIds.size();i++){
            TwitterProfile profile = sampleProfile();
            profile.setTwitterId(profileIds.get(i));
            profile.setIndex(i);
            ArrayList<Integer> folloers = new ArrayList<>();
            int setrandomFollowers = random.nextInt(50);
            for(int j=0;j<=setrandomFollowers;j++){
                folloers.add(profileIds.get(random.nextInt(profileIds.size())));
            }
            profile.setFollowersList(folloers);
            indexedprofileHashMap.put(i, profile);
            profileHashMap.put(profile.getTwitterId(), profile);
        }

    }

    public static TwitterProfile sampleProfile(){
        Random random = new Random();
        TwitterProfile profile = new TwitterProfile();
        profile.setFollowers(4000 + random.nextInt(10000));
        profile.setFollowing(1000 + random.nextInt(5000));
        return profile;
    }
}
