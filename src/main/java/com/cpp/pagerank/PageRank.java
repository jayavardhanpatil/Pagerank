package com.cpp.pagerank;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;

/**
 * Created by IntelliJ IDEA.
 * User: jayavardhanpatil
 * Date: 11/19/20
 * Time:  17:27
 */

public class PageRank {

    private static HashMap<BigInteger, TwitterProfile> profileHashMap = new HashMap<>();
    private static ArrayList<Integer> indexedprofileId = new ArrayList<>();
    static TreeMap<Double, TwitterProfile> rankedProfiles = new TreeMap<>(Collections.reverseOrder());
    private static HashMap<BigInteger, Integer> incomingCount = new HashMap<>();

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

            ArrayList<HashMap<Integer, Double>> pageMat = new ArrayList<>(Collections.nCopies(totalVertices, null));

            //initialize the first
            double initializethePageRank = (double) (1.0 / totalVertices);
            Arrays.fill(pageRank, initializethePageRank);

            double[] currentPageRank = Arrays.copyOf(pageRank, pageRank.length);

            //fill the matrix
            for (int j = 0; j < totalVertices; j++) {

                ArrayList<BigInteger> followers = (ArrayList<BigInteger>) profileHashMap.get(indexedprofileId.get(j)).getFollowersList();

                int followersCount = profileHashMap.get(indexedprofileId.get(j)).getFollowers();

                for (BigInteger twitterId : followers) {
                    int getProfileIdex = profileHashMap.get(twitterId).getIndex();
                   //pageMatrix[j][getProfileIdex] = (double) 1.0 / followersCount;
                    double value = (double) (1.0 / followersCount);
                    HashMap<Integer, Double> list = pageMat.get(getProfileIdex);
                    if(list == null) list = new HashMap<>();
                    list.put(j, value);
                    pageMat.set(getProfileIdex, list);
                }
            }

                        //calculatePageRank(pageMatrix, pageRank, currentPageRank, pageMat);

            System.out.println("Sum : " + Arrays.stream(currentPageRank).sum());

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
        ArrayList<HashMap<Integer, Double>> listOfEdges = new ArrayList<>();

        HashMap<Integer, Double> map = new HashMap<>(); map.put(1, 1.0);map.put(2, 1.0/8);map.put(3, 1.0/3);map.put(4, 1.0/3);map.put(5, 1.0/2);map.put(6, 1.0);map.put(7, 1.0);listOfEdges.add(map);
        map = new HashMap<>(); map.put(2, 1.0/8); listOfEdges.add(map);
        map = new HashMap<>(); map.put(2, 1.0/8);map.put(3, 1.0/3); listOfEdges.add(map);
        map = new HashMap<>(); map.put(2, 1.0/8); listOfEdges.add(map);
        map = new HashMap<>(); map.put(2, 1.0/8); listOfEdges.add(map);
        map = new HashMap<>(); map.put(2, 1.0/8);map.put(4, 1.0/3); listOfEdges.add(map);
        map = new HashMap<>(); map.put(2, 1.0/8);map.put(3, 1.0/3);map.put(4, 1.0/3);map.put(5, 1.0/2); listOfEdges.add(map);
        map = new HashMap<>(); map.put(0,1.0); map.put(2, 1.0/8); listOfEdges.add(map);
        double[] pageRank = new double[8];
        Arrays.fill(pageRank, (1.0)/pageRank.length);
        double[] currentPageRank = Arrays.copyOf(pageRank, pageRank.length);
        calculatePageRank(pageRank, currentPageRank, listOfEdges);
    }

    public static void calculatePageRank(double[]pageRank, double[] currentPageRank, ArrayList<HashMap<Integer, Double>> pageMat){
        boolean isChangeInPageRankVal = true;
        int iterations = 0;

        //double[] previousPageRank = Arrays.copyOf(pageRank, pageRank.length);

        while(isChangeInPageRankVal && iterations < 100){
            isChangeInPageRankVal = false;
            int k=0;
            for(;k<pageMat.size();k++){
                double current_page_rank = 0.0;
                //double current_page_rank1 = 0.0;

                if(pageMat.get(k) == null) {
                    System.out.println("Index : "+ k);
                    continue;
                }

                for (Map.Entry<Integer, Double> j : pageMat.get(k).entrySet()) {
                        current_page_rank += j.getValue() * pageRank[j.getKey()];
                    }

//                if(!istest) {
//
////                    for (int followers : indexedprofileHashMap.get(k).getFollowersList()) {
////                        int getProfileIdex = profileHashMap.get(followers).getIndex();
////                        //current_page_rank += pageMatrix[k][getProfileIdex] * pageRank[getProfileIdex];
////                        current_page_rank += pageMat.get(k).get(getProfileIdex) * pageRank[getProfileIdex];
////                    }
//
////                    for(int followers : indexedprofileHashMap.get(k).getFollowersList()){
////                        int getProfileIdex = profileHashMap.get(followers).getIndex();
////
////                    }
//                    //System.out.println(BigDecimal.valueOf(current_page_rank).toPlainString());
//
//                }else {
//                    for (Map.Entry<Integer, Double> j : pageMat.get(k).entrySet()) {
//                        current_page_rank += j.getValue() * pageRank[j.getKey()];
//                    }
//                }

                current_page_rank = ((0.15) / pageRank.length) + (0.85 * current_page_rank);
                if(!isChangeInPageRankVal && Math.abs(currentPageRank[k] - current_page_rank) >= 0.000001){
                    isChangeInPageRankVal = true;
                }
                currentPageRank[k] = current_page_rank;
            }
            System.out.println("Sum : " + Arrays.stream(pageRank).sum());
            pageRank = currentPageRank;
            System.out.println("After assign Sum : " + Arrays.stream(currentPageRank).sum());
            iterations++;
        }
        System.out.println("Total Iterations : "+iterations);

        for (int i=0;i<currentPageRank.length;i++){
            //System.out.println(currentPageRank[i]);
            rankedProfiles.put(currentPageRank[i], profileHashMap.get(indexedprofileId.get(i)));
        }

        System.out.println("Sum : " + Arrays.stream(currentPageRank).sum());

        int count = 0;
        for(Map.Entry<Double, TwitterProfile> data : rankedProfiles.entrySet()){
            System.out.println(BigDecimal.valueOf(data.getKey()).toPlainString());
            System.out.println(data.getValue());
            count++;
            if(count > 10) break;
        }
    }

    public static void generateTwitterProfileSamples() throws IOException {
//        Random random = new Random();
//
//        ArrayList<Integer> profileIds = new ArrayList<>();
//        for(int i = 0;i<totalNodes;i++){
//            int randomNumber = 1000000 + random.nextInt(9000000);
//            if (profileIds.contains(randomNumber)) continue;
//            else profileIds.add(randomNumber);
//
//        }
//
//        for(int i =0;i<profileIds.size();i++){
//            TwitterProfile profile = sampleProfile();
//            profile.setTwitterId(profileIds.get(i));
//            profile.setIndex(i);
//            HashSet<Integer> folloers = new HashSet<>();
//            int setrandomFollowers = 10 + random.nextInt(90);
//            for(int j=0;j<setrandomFollowers;j++){
//                folloers.add(profileIds.get(random.nextInt(profileIds.size())));
//            }
//            profile.setFollowersList(new ArrayList<>(folloers));
//            profile.setFollowers(folloers.size());
//            indexedprofileId.add(profile.getTwitterId());
//            profileHashMap.put(profile.getTwitterId(), profile);
//        }
    	
    	
    	//Read friendships file and convert it into a JSONObject
    	JSONParser jsonParser = new JSONParser();
        try
        {
        	Object obj = jsonParser.parse(new FileReader("Inputs/cache/friendships.json"));
            JSONObject jsonObject = (JSONObject)obj;
            
            //Get all twitter ids from document
            Set<String> keys = jsonObject.keySet();
            int index =0;
            
            for(String key : keys)
            {
            	index++;
            	
            	//Get list of following profiles
            	JSONArray jsonArray = (JSONArray) jsonObject.get(key);
            	ArrayList<BigInteger> followings = new ArrayList<>();
            	for(int i=0; i<jsonArray.size(); i++)
            	{
            		BigInteger followingID = new BigInteger((String) jsonArray.get(i));
            		
            		//Increment the followers count for each profile listed
            		if(profileHashMap.containsKey(followingID))
            		{
            			TwitterProfile twitterProfile = profileHashMap.get(followingID);
            			int followers = twitterProfile.getFollowers();
            			twitterProfile.setFollowers(followers++);
            		}
            		else
            		{
            			if(incomingCount.containsKey(followingID))
            			{
            				int followerCount = incomingCount.get(followingID);
            				followerCount +=1;
            				incomingCount.replace(followingID, followerCount);
            			}
            			else
            				incomingCount.put(followingID, 1);
            		}
            		
            		followings.add(followingID);
            	}
            	
            	//Setup twitter profile for all users from the file
            	
            	TwitterProfile profile = new TwitterProfile();
            	profile.setTwitterId(new BigInteger(key));
            	if(incomingCount.containsKey(new BigInteger(key)))
            	{
            		profile.setFollowers(incomingCount.get(new BigInteger(key)));
            	}
            	else
            		profile.setFollowers(0);
            	
            	profile.setFollowing(followings.size());
            	profile.setFollowersList(followings);
            	profile.setIndex(index);
            	
            	profileHashMap.put(new BigInteger(key), profile);
            	
            }
           

            profileHashMap.entrySet().forEach(entry->{
                System.out.println(entry.getKey() + " " + entry.getValue());  
             });
        }
        catch(Exception E)
        {
        	E.printStackTrace();
        }
    }

    public static TwitterProfile sampleProfile(){
        Random random = new Random();
        TwitterProfile profile = new TwitterProfile();
        profile.setFollowers(10 + random.nextInt(90));
        profile.setFollowing(10 + random.nextInt(90));
        return profile;
    }
}
