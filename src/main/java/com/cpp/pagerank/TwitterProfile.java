package com.cpp.pagerank;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Created by IntelliJ IDEA.
 * User: jayavardhanpatil
 * Date: 11/19/20
 * Time:  17:27
 */
public class TwitterProfile {

    private BigInteger twitterId;
    private int followers;
    private int following;
    private int index;
    List<BigInteger> followersList;

    public TwitterProfile() {
    }

    public TwitterProfile(BigInteger twitterId, int followers, int following, List<BigInteger> followersList) {
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

    public BigInteger getTwitterId() {
        return twitterId;
    }

    public void setTwitterId(BigInteger twitterId) {
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

    public List<BigInteger> getFollowersList() {
        return followersList;
    }

    public void setFollowersList(List<BigInteger> followersList) {
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