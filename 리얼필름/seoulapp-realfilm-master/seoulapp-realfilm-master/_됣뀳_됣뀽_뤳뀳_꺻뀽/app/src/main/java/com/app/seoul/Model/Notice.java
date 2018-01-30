package com.app.seoul.Model;

/**
 * Created by ihoyong on 2017. 10. 21..
 */

public class Notice {

    private String title, content, date;

    public Notice(String title, String content, String date) {
        this.title = title;
        this.content = content;
        this.date = date;
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
}
