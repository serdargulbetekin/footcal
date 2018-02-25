package com.sgfootcal.android.footcal.pojomodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by serdar on 18.8.2017.
 */

public class Coach implements Serializable {


    @SerializedName("Coach_Id")
    @Expose
    private String Coach_Id;
    @SerializedName("Coach_Name")
    @Expose
    private String Coach_Name;
    @SerializedName("Team_Id")
    @Expose
    private com.sgfootcal.android.footcal.pojomodel.Teams Teams;
    @SerializedName("Country_Id")
    @Expose
    private com.sgfootcal.android.footcal.pojomodel.Country Country;

    public String getCoach_Id() {
        return Coach_Id;
    }

    public void setCoach_Id(String coach_Id) {
        Coach_Id = coach_Id;
    }

    public String getCoach_Name() {
        return Coach_Name;
    }

    public void setCoach_Name(String coach_Name) {
        Coach_Name = coach_Name;
    }

    public com.sgfootcal.android.footcal.pojomodel.Teams getTeams() {
        return Teams;
    }

    public void setTeams(com.sgfootcal.android.footcal.pojomodel.Teams teams) {
        Teams = teams;
    }

    public com.sgfootcal.android.footcal.pojomodel.Country getCountry() {
        return Country;
    }

    public void setCountry(com.sgfootcal.android.footcal.pojomodel.Country country) {
        Country = country;
    }
}
