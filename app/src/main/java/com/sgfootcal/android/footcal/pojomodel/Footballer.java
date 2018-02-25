
package com.sgfootcal.android.footcal.pojomodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Footballer implements Serializable{

    @SerializedName("Footballer_Id")
    @Expose
    private String Footballer_Id;

    @SerializedName("Footballer_Name")
    @Expose
    private String Footballer_Name;

    @SerializedName("Footballer_ShirtNumber")
    @Expose
    private String Footballer_ShirtNumber;

    @SerializedName("Footballer_Position")
    @Expose
    private String Footballer_Position;

    @SerializedName("Team_Id")
    @Expose
    private com.sgfootcal.android.footcal.pojomodel.Teams Teams;

    @SerializedName("Country_Id")
    @Expose
    private com.sgfootcal.android.footcal.pojomodel.Country Country;

    @SerializedName("GoalScore_Id")
    @Expose
    private String GoalScore_Id;

    @SerializedName("AssistScore_Id")
    @Expose
    private String AssistScore_Id;

    @SerializedName("YellowCard")
    @Expose
    private String YellowCard;

    @SerializedName("RedCard")
    @Expose
    private String RedCard;



    public String getFootballer_Id() {
        return Footballer_Id;
    }

    public void setFootballer_Id(String footballer_Id) {
        Footballer_Id = footballer_Id;
    }

    public String getFootballer_Name() {
        return Footballer_Name;
    }

    public void setFootballer_Name(String footballer_Name) {
        Footballer_Name = footballer_Name;
    }

    public String getFootballer_ShirtNumber() {
        return Footballer_ShirtNumber;
    }

    public void setFootballer_ShirtNumber(String footballer_ShirtNumber) {
        Footballer_ShirtNumber = footballer_ShirtNumber;
    }

    public String getFootballer_Position() {
        return Footballer_Position;
    }

    public void setFootballer_Position(String footballer_Position) {
        Footballer_Position = footballer_Position;
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

    public String getGoalScore_Id() {
        return GoalScore_Id;
    }

    public void setGoalScore_Id(String goalScore_Id) {
        GoalScore_Id = goalScore_Id;
    }

    public String getAssistScore_Id() {
        return AssistScore_Id;
    }

    public void setAssistScore_Id(String assistScore_Id) {
        AssistScore_Id = assistScore_Id;
    }

    public String getYellowCard() {
        return YellowCard;
    }

    public void setYellowCard(String yellowCard) {
        YellowCard = yellowCard;
    }

    public String getRedCard() {
        return RedCard;
    }

    public void setRedCard(String redCard) {
        RedCard = redCard;
    }
}
