package com.app.seoul.Model;

/**
 * Created by ihoyong on 2017. 9. 19..
 */

public class search {

    private int id;
    private String title, image;

    public search(int id, String title, String image) {
        this.id = id;
        this.title = title;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }
}
