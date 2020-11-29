package com.cpp.pagerank;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jayavardhanpatil
 * Date: 11/19/20
 * Time:  17:27
 */
public class TwitterProfile {

    private String twitterId;
    private int followers;
    private int following;
    List<String> followingsList;
    private double rank;

    public String getTwitterId() {
        return twitterId;
    }

    public void setTwitterId(String twitterId) {
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

    public List<String> getFollowingsList() {
        return followingsList;
    }

    public void setFollowingsList(List<String> followingsList) {
        this.followingsList = followingsList;
    }

    public double getRank() {
        return rank;
    }

    public void setRank(double rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "{" +
                "twitterId='" + twitterId + '\'' +
                ", followers=" + followers +
                ", following=" + following +
                ", rank=" + rank +
                '}';
    }

}