
package com.sgfootcal.android.footcal.pojomodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Fixture implements Serializable{

    @SerializedName("Fixture_Id")
    @Expose
    private String Fixture_Id;
    @SerializedName("Fixture_Date")
    @Expose
    private String Fixture_Date;
    @SerializedName("Fixture_Day")
    @Expose
    private String Fixture_Day;
    @SerializedName("Fixture_Hour")
    @Expose
    private String Fixture_Hour;
    @SerializedName("Teams")
    @Expose
    private Teams Teams;
    @SerializedName("Teams2")
    @Expose
    private Teams Teams2;
    @SerializedName("League")
    @Expose
    private Leagues Leagues;
    @SerializedName("Referee")
    @Expose
    private Referee Referee;
    @SerializedName("Result")
    @Expose
    private FixtureResult FixtureResult;

    public String getFixture_Id() {
        return Fixture_Id;
    }

    public void setFixture_Id(String fixture_Id) {
        Fixture_Id = fixture_Id;
    }

    public String getFixture_Date() {
        return Fixture_Date;
    }

    public void setFixture_Date(String fixture_Date) {
        Fixture_Date = fixture_Date;
    }

    public String getFixture_Day() {
        return Fixture_Day;
    }

    public void setFixture_Day(String fixture_Day) {
        Fixture_Day = fixture_Day;
    }

    public String getFixture_Hour() {
        return Fixture_Hour;
    }

    public void setFixture_Hour(String fixture_Hour) {
        Fixture_Hour = fixture_Hour;
    }

    public com.sgfootcal.android.footcal.pojomodel.Teams getTeams() {
        return Teams;
    }

    public void setTeams(com.sgfootcal.android.footcal.pojomodel.Teams teams) {
        Teams = teams;
    }

    public com.sgfootcal.android.footcal.pojomodel.Teams getTeams2() {
        return Teams2;
    }

    public void setTeams2(com.sgfootcal.android.footcal.pojomodel.Teams teams2) {
        Teams2 = teams2;
    }

    public com.sgfootcal.android.footcal.pojomodel.Leagues getLeagues() {
        return Leagues;
    }

    public void setLeagues(com.sgfootcal.android.footcal.pojomodel.Leagues leagues) {
        Leagues = leagues;
    }

    public com.sgfootcal.android.footcal.pojomodel.Referee getReferee() {
        return Referee;
    }

    public void setReferee(com.sgfootcal.android.footcal.pojomodel.Referee referee) {
        Referee = referee;
    }

    public com.sgfootcal.android.footcal.pojomodel.FixtureResult getFixtureResult() {
        return FixtureResult;
    }

    public void setFixtureResult(com.sgfootcal.android.footcal.pojomodel.FixtureResult fixtureResult) {
        FixtureResult = fixtureResult;
    }
}
