package com.cpp.pagerank;

import java.util.Comparator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jayavardhanpatil
 * Date: 11/19/20
 * Time:  17:27
 */
public class TwitterProfile {

    private int twitterId;
    private int followers;
    private int following;
    private int index;
    List<Integer> followersList;

    public TwitterProfile() {
    }

    public TwitterProfile(int twitterId, int followers, int following, List<Integer> followersList) {
        this.twitterId = twitterId;
        this.followers = followers;
        this.following = following;
        this.followersList = followersList;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getTwitterId() {
        return twitterId;
    }

    public void setTwitterId(int twitterId) {
        this.twitterId = twitterId;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public List<Integer> getFollowersList() {
        return followersList;
    }

    public void setFollowersList(List<Integer> followersList) {
        this.followersList = followersList;
    }


    @Override
    public String toString() {
        return "{" +
                "twitterId=" + twitterId +
                ", followers=" + followers +
                ", following=" + following +
                ", index=" + index +
                ", followersList=" + followersList +
                '}';
    }
}