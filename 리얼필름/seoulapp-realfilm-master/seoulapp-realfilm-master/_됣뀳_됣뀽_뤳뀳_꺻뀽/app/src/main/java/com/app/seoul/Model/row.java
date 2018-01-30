package com.app.seoul.Model;

/**
 * Created by ihoyong on 2017. 10. 22..
 */

public class row {


    private int CULTCODE;

    private int SUBJCODE;

    private String CODENAME;

    private String TITLE;

    private String STRTDATE;

    private String ENDDATE;

    private String PLACE;

    private String MAIN_IMG;

    public row(int CULTCODE, int SUBJCODE, String CODENAME, String TITLE, String STRTDATE, String ENDDATE, String PLACE, String MAIN_IMG) {
        this.CULTCODE = CULTCODE;
        this.SUBJCODE = SUBJCODE;
        this.CODENAME = CODENAME;
        this.TITLE = TITLE;
        this.STRTDATE = STRTDATE;
        this.ENDDATE = ENDDATE;
        this.PLACE = PLACE;
        this.MAIN_IMG = MAIN_IMG;
    }

    public int getCULTCODE() {

        return CULTCODE;
    }

    public int getSUBJCODE() {
        return SUBJCODE;
    }

    public String getCODENAME() {
        return CODENAME;
    }

    public String getTITLE() {
        return TITLE;
    }

    public String getSTRTDATE() {
        return STRTDATE;
    }

    public String getENDDATE() {
        return ENDDATE;
    }

    public String getPLACE() {
        return PLACE;
    }

    public String getMAIN_IMG() {
        return MAIN_IMG;
    }
}
