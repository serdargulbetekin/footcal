
package com.sgfootcal.android.footcal.pojomodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Teams implements Serializable {

    @SerializedName("Team_Id")
    @Expose
    private String Team_Id;

    @SerializedName("Team_Name")
    @Expose
    private String Teams_Name;

    @SerializedName("Team_Photo")
    @Expose
    private String Teams_Photo;

    @SerializedName("Team_Stadium")
    @Expose
    private String Team_Stadium;

    @SerializedName("Team_City")
    @Expose
    private String Teams_City;

    @SerializedName("Team_Country")
    @Expose
    private Country Team_Country;

    @SerializedName("League")
    @Expose
    private Leagues leagues;

    @SerializedName("Team_GF")
    @Expose
    private String Team_GF;

    @SerializedName("Team_GA")
    @Expose
    private String Team_GA;

    @SerializedName("Team_Point")
    @Expose
    private String Team_Point;

    @SerializedName("Team_Week")
    @Expose
    private String Team_Week;

    public String getTeam_Id() {
        return Team_Id;
    }

    public void setTeam_Id(String team_Id) {
        Team_Id = team_Id;
    }

    public String getTeams_Name() {
        return Teams_Name;
    }

    public void setTeams_Name(String teams_Name) {
        Teams_Name = teams_Name;
    }

    public String getTeams_Photo() {
        return Teams_Photo;
    }

    public void setTeams_Photo(String teams_Photo) {
        Teams_Photo = teams_Photo;
    }

    public String getTeams_Staidum() {
        return Team_Stadium;
    }

    public void setTeams_Staidum(String teams_Staidum) {
        Team_Stadium = teams_Staidum;
    }

    public String getTeams_City() {
        return Teams_City;
    }

    public void setTeams_City(String teams_City) {
        Teams_City = teams_City;
    }

    public Country getTeam_Country() {
        return Team_Country;
    }

    public void setTeam_Country(Country team_Country) {
        Team_Country = team_Country;
    }

    public Leagues getLeagues() {
        return leagues;
    }

    public void setLeagues(Leagues leagues) {
        this.leagues = leagues;
    }

    public String getTeam_GF() {
        return Team_GF;
    }

    public void setTeam_GF(String team_GF) {
        Team_GF = team_GF;
    }

    public String getTeam_GA() {
        return Team_GA;
    }

    public void setTeam_GA(String team_GA) {
        Team_GA = team_GA;
    }

    public String getTeam_Point() {
        return Team_Point;
    }

    public void setTeam_Point(String team_Point) {
        Team_Point = team_Point;
    }

    public String getTeam_Week() {
        return Team_Week;
    }

    public void setTeam_Week(String team_Week) {
        Team_Week = team_Week;
    }
}
