package com.sgfootcal.android.footcal.pojomodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by serdar on 18.8.2017.
 */

public class ClubChairman implements Serializable {


    @SerializedName("ClupChairman_Id")
    @Expose
    private String ClubChairman_Id;
    @SerializedName("ClupChairman_Name")
    @Expose
    private String ClubChairman_Name;
    @SerializedName("Team_Id")
    @Expose
    private Teams Teams;
    @SerializedName("Country_Id")
    @Expose
    private Country Country;

    public String getClubChairman_Id() {
        return ClubChairman_Id;
    }

    public void setClubChairman_Id(String clubChairman_Id) {
        ClubChairman_Id = clubChairman_Id;
    }

    public String getClubChairman_Name() {
        return ClubChairman_Name;
    }

    public void setClubChairman_Name(String clubChairman_Name) {
        ClubChairman_Name = clubChairman_Name;
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
