package com.app.seoul.Model;

import java.util.List;

/**
 * Created by ihoyong on 2017. 10. 22..
 */

public class SearchPerformanceBySubjectService {


    private List<com.app.seoul.Model.row> row;

    public SearchPerformanceBySubjectService(List<com.app.seoul.Model.row> row) {
        this.row = row;
    }

    public List<com.app.seoul.Model.row> getRow() {
        return row;
    }


}
