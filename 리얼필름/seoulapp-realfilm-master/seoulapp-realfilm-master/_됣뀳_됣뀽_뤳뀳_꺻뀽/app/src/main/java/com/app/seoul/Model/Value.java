package com.app.seoul.Model;

import java.util.List;

/**
 * Created by ihoyong on 2017. 9. 15..
 */

public class Value {

    private String value, message, id, pimage, pback;
    private SearchPerformanceBySubjectService SearchPerformanceBySubjectService;

    private List<board> board;
    private List<search> search;
    private List<Notice> notice;

    private List<row> row;
    private List<comment> comment;

    private List<pro_a> pro_a;
    private List<pro_b> pro_b;
    private List<up_aa> up_a;
    private List<up_bb> up_b;
    private List<up_cc> up_c;
    private List<search_a> search_a;
    private List<search_c> search_c;

    public List<com.app.seoul.Model.search_c> getSearch_c() {
        return search_c;
    }

    public List<com.app.seoul.Model.search_a> getSearch_a() {
        return search_a;
    }

    private com.app.seoul.Model.Food.response response;

    public com.app.seoul.Model.Food.response getResponse() {
        return response;
    }

    public List<up_cc> getUp_c() {
        return up_c;
    }

    public List<up_bb> getUp_b() {
        return up_b;
    }

    public List<up_aa> getUp_a() {
        return up_a;
    }

    public String getPback() {
        return pback;
    }

    public String getPimage() {
        return pimage;
    }

    public List<com.app.seoul.Model.pro_b> getPro_b() {
        return pro_b;
    }

    public List<com.app.seoul.Model.pro_a> getPro_a() {
        return pro_a;
    }

    public List<com.app.seoul.Model.comment> getComment() {
        return comment;
    }

    public String getId() {
        return id;
    }

    public com.app.seoul.Model.SearchPerformanceBySubjectService getSearchPerformanceBySubjectService() {
        return SearchPerformanceBySubjectService;
    }

    public List<com.app.seoul.Model.row> getRow() {
        return row;
    }

    public List<Notice> getNotice() {
        return notice;
    }

    public List<com.app.seoul.Model.search> getSearch() {
        return search;
    }

    public String getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public List<com.app.seoul.Model.board> getBoard() {
        return board;
    }
}
