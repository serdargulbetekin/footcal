package com.sgfootcal.android.footcal.pojomodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by serdar on 18.8.2017.
 */

public class Leagues implements Serializable {


    @SerializedName("League_Id")
    @Expose
    private String Leagues_Id;
    @SerializedName("League_Name")
    @Expose
    private String Leagues_Name;
    @SerializedName("League_Photo")
    @Expose
    private String Leagues_Photo;


    public String getLeagues_Id() {
        return Leagues_Id;
    }

    public void setLeagues_Id(String leagues_Id) {
        Leagues_Id = leagues_Id;
    }

    public String getLeagues_Name() {
        return Leagues_Name;
    }

    public void setLeagues_Name(String leagues_Name) {
        Leagues_Name = leagues_Name;
    }

    public String getLeagues_Photo() {
        return Leagues_Photo;
    }

    public void setLeagues_Photo(String leagues_Photo) {
        Leagues_Photo = leagues_Photo;
    }
}
