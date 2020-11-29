package com.cpp.pagerank;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * User: jayavardhanpatil
 * Date: 11/19/20
 * Time:  17:27
 */

public class PageRank {

    public static HashMap<String, TwitterProfile> profileHashMap = new HashMap<>();
    public static List<String> indexedprofileId = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        //Read the Crawled Twitter profiles and generate network
        generateTwitterProfileNetwork();

        //Calculate pageRank
        pageRankCalculate();
    }

    public static void pageRankCalculate() throws IOException {
        int totalVertices = profileHashMap.size();

        indexedprofileId = new ArrayList<>(profileHashMap.keySet());

        ArrayList<HashMap<Integer, Double>> pageMat = new ArrayList<>(Collections.nCopies(totalVertices, null));

        //Form a matrix type data structure.
        //Using ArrayList<HashMap> instead Matrix to optimize the Space and Time Complexity
        for (int j = 0; j < totalVertices; j++) {
            ArrayList<String> followings = (ArrayList<String>) profileHashMap.get(indexedprofileId.get(j)).getFollowingsList();
            int followingsCount = followings.size();

            for (String twitterId : followings) {
                int getProfileIdex = indexedprofileId.indexOf(twitterId);
                double value = (1.0 / followingsCount);
                HashMap<Integer, Double> list = pageMat.get(getProfileIdex);
                if (list == null) list = new HashMap<>();
                list.put(j, value);
                pageMat.set(getProfileIdex, list);
            }
        }

        double[] pageRank = new double[totalVertices];

        //Initialize the Initialize the Page Rank
        double initializethePageRank = (1.0 / (totalVertices));
        Arrays.fill(pageRank, initializethePageRank);

        //To store current iteration PageRank values
        double[] currentPageRank = Arrays.copyOf(pageRank, pageRank.length);

        System.out.println("Sum : " + Arrays.stream(currentPageRank).sum());
        LocalDateTime startTime = LocalDateTime.now();
        calculatePageRank(pageRank, currentPageRank, pageMat);
        LocalDateTime endTime = LocalDateTime.now();
        System.out.println(startTime.until(endTime, ChronoUnit.SECONDS) + " Seconds");
    }


    //Calcualet PageRank on Matrix and delta of 0.85.
    //using 0.00001 threshold to check from previous pagerank value.
    //worstcase it iterates for 100 times
    public static void calculatePageRank(double[]pageRank, double[] currentPageRank, ArrayList<HashMap<Integer, Double>> pageMat) throws IOException {
        boolean isChangeInPageRankVal = true;
        int iterations = 0;

        //double[] previousPageRank = Arrays.copyOf(pageRank, pageRank.length);

        while(isChangeInPageRankVal && iterations < 100){
            isChangeInPageRankVal = false;
            int k=0;
            for(;k<pageMat.size();k++){
                double current_page_rank = 0.0;

                if(pageMat.get(k) == null) {
                    continue;
                }

                for (Map.Entry<Integer, Double> j : pageMat.get(k).entrySet()) {
                        current_page_rank += j.getValue() * pageRank[j.getKey()];
                    }

                current_page_rank = ((0.15) / pageRank.length) + (0.85 * current_page_rank);
                if(!isChangeInPageRankVal && Math.abs(currentPageRank[k] - current_page_rank) >= 0.00001){
                    isChangeInPageRankVal = true;
                }
                currentPageRank[k] = current_page_rank;
            }

            pageRank = currentPageRank;
            iterations++;
        }
        System.out.println("Total Iterations : "+iterations);

        System.out.println(Arrays.toString(pageRank));



        for (int i=0;i<currentPageRank.length;i++){
            profileHashMap.get(indexedprofileId.get(i)).setRank(currentPageRank[i]);
        }


        //Validate if all nodes sums up tp 1.0
        System.out.println("Sum : " + Arrays.stream(currentPageRank).sum());

        //Writing to file to highlight on the graph
        FileWriter writer = new FileWriter("out_put_pageRank.csv");
        writer.append("twitter_Id,page_rank_value,rank\n");

        int index = 1;
        System.out.println(profileHashMap);
        Map<String, TwitterProfile> result = profileHashMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.comparingDouble(TwitterProfile::getRank)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));


        System.out.println(result);

        for(Map.Entry<String, TwitterProfile> data : result.entrySet()){
            writer.append(data.getValue().getTwitterId()+","+data.getValue().getRank()+","+index);
            writer.append("\n");
            index++;
        }
        writer.flush();
    }

    //Read Json Crawled data and create graph-network.
    public static void generateTwitterProfileNetwork() {

        //Read friendships file and convert it into a JSONObject
        JSONParser jsonParser = new JSONParser();
        try( FileReader fileReader = new FileReader("src/main/resources/friendships.json")){

            JSONObject jsonObject = (JSONObject) jsonParser.parse(fileReader);
            Set<String> keys = jsonObject.keySet();
            for (String key : keys) {
                TwitterProfile profile = new TwitterProfile();
                profile.setTwitterId(key);
                profileHashMap.put(key, profile);
            }

            for (String key : keys) {
                JSONArray jsonArray = (JSONArray) jsonObject.get(key);
                ArrayList<String> followings = new ArrayList<>();
                TwitterProfile node = profileHashMap.get(key);
                for (Object o : jsonArray) {
                    String followingId = (String) o;
                    //Increment the followers count for each profile listed
                    if (profileHashMap.containsKey(followingId)) {
                        TwitterProfile twitterProfile = profileHashMap.get(followingId);
                        int followers = twitterProfile.getFollowers();
                        twitterProfile.setFollowers(++followers);
                        followings.add(followingId);
                    }
                }
                node.setFollowing(followings.size());
                node.setFollowingsList(followings);
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        profileHashMap.entrySet().removeIf((entry) -> entry.getValue().getFollowers() == 0);
    }
}
