package com.cpp.pagerank;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import static com.cpp.pagerank.PageRank.profileHashMap;

/**
 * Created by IntelliJ IDEA.
 * User: jayavardhanpatil
 * Date: 11/29/20
 * Time:  11:47
 */

public class Utils {

    public static void generateTwitterProfileSamples(int totalNodes) {
        Random random = new Random();

        ArrayList<String> profileIds = new ArrayList<>();
        for(int i = 0;i<totalNodes;i++){
            int randomNumber = 1000000 + random.nextInt(9000000);
            if (profileIds.contains(String.valueOf(randomNumber))) {
                continue;
            } else profileIds.add(String.valueOf(randomNumber));
        }

        for(int i =0;i<profileIds.size();i++){
            TwitterProfile profile = sampleProfile();
            profile.setTwitterId(profileIds.get(i));
            HashSet<String> folloers = new HashSet<>();
            int setrandomFollowers = 10 + random.nextInt(90);
            for(int j=0;j<setrandomFollowers;j++){
                folloers.add(profileIds.get(random.nextInt(profileIds.size())));
            }
            profile.setFollowingsList(new ArrayList<>(folloers));
            profile.setFollowers(folloers.size());
            profileHashMap.put(profile.getTwitterId(), profile);
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