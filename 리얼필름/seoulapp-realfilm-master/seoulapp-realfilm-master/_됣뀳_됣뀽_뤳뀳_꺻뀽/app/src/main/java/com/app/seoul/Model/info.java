package com.app.seoul.Model;

import java.util.List;

/**
 * Created by ihoyong on 2017. 10. 23..
 */

public class info {

    private String title, content, date, locationName, profile, name, commentcount, popupc, vcount;
    private double lati, longi;
    private int likecount, userclick, bookm, fallo;
    private List<String> imagearray;
    private List<Integer> tagarray;

    public String getVcount() {
        return vcount;
    }

    public int getFallo() {
        return fallo;
    }

    public String getPopupc() {
        return popupc;
    }

    public String getCommentcount() {
        return commentcount;
    }

    public int getBookm() {
        return bookm;
    }

    public int getUserclick() {
        return userclick;
    }

    public String getName() {
        return name;
    }

    public String getProfile() {
        return profile;
    }

    public String getTitle() {

        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public String getLocationName() {
        return locationName;
    }

    public double getLati() {
        return lati;
    }

    public double getLongi() {
        return longi;
    }

    public int getLikecount() {
        return likecount;
    }

    public List<String> getImagearray() {
        return imagearray;
    }

    public List<Integer> getTagarray() {
        return tagarray;
    }
}
