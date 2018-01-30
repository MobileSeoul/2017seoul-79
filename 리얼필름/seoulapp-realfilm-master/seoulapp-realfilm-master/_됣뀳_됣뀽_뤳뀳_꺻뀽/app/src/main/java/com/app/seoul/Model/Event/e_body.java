package com.app.seoul.Model.Event;

/**
 * Created by ihoyong on 2017. 10. 27..
 */

public class e_body {

    private e_items items;
    private int numOfRows, pageNo, totalCount;


    public e_items getItems() {
        return items;
    }

    public int getNumOfRows() {
        return numOfRows;
    }

    public int getPageNo() {
        return pageNo;
    }

    public int getTotalCount() {
        return totalCount;
    }
}
