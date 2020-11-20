package com.cpp.pagerank;

import java.io.IOException;
import java.math.BigDecimal;
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

    public static void main(String[] args) throws IOException {

        try {
            generateTwitterProfileSamples();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Sample profiles");

        int totalVertices = profileHashMap.size();

        double[][] pageMatrix = new double[totalVertices][totalVertices];
        double[] pageRank = new double[totalVertices];

        //initialize the first
        double initializethePageRank = (double) (1.0/totalVertices);
        Arrays.fill(pageRank, initializethePageRank);

        double[] currentPageRank = new double[totalVertices];

        //fill the matrix
        for(int j=0;j<pageMatrix.length;j++){
            ArrayList<Integer> followers = (ArrayList<Integer>) indexedprofileHashMap.get(j).getFollowersList();
            int followersCount = indexedprofileHashMap.get(j).getFollowers();
            for(int twitterId : followers){
                int getProfileIdex = profileHashMap.get(twitterId).getIndex();
                pageMatrix[j][getProfileIdex] = (double) 1.0 / followersCount;
            }
        }
        calculatePageRank(pageMatrix, pageRank, currentPageRank);
    }

    public static void calculatePageRank(double[][] pageMatrix, double[]pageRank, double[] currentPageRank){
        boolean isChangeInPageRankVal = true;
        int iterations = 0;

        double[] previousPageRank = Arrays.copyOf(pageRank, pageRank.length);

        while(isChangeInPageRankVal && iterations < 10000){
            isChangeInPageRankVal = false;
            for(int k=0;k<pageMatrix.length;k++){
                double current_page_rank = 0.0;
                for(int followers : indexedprofileHashMap.get(k).getFollowersList()){
                    int getProfileIdex = profileHashMap.get(followers).getIndex();
                    current_page_rank +=  pageMatrix[k][getProfileIdex] * pageRank[getProfileIdex];
                }

                current_page_rank = ((0.15) / pageRank.length) + (0.85 * current_page_rank);
                if(!isChangeInPageRankVal && Math.abs(previousPageRank[k] - current_page_rank) >= 0.0001){
                    isChangeInPageRankVal = true;
                }

                previousPageRank[k] = current_page_rank;
                currentPageRank[k] = current_page_rank;
            }
            pageRank = previousPageRank;
            iterations++;
        }
        System.out.println("Total Iterations : "+iterations);

        for (int i=0;i<currentPageRank.length;i++){
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
        for(int i = 0;i<10000;i++){
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
