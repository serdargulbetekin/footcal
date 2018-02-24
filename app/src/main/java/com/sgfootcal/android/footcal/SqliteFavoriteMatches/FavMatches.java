package com.sgfootcal.android.footcal.SqliteFavoriteMatches;


import java.io.Serializable;



public class FavMatches implements Serializable {




    private int matchNo;
    private String firstTeam_Id;

    public FavMatches() {
    }

    public FavMatches(int matchNo, String firstTeam_Id) {
        this.matchNo = matchNo;
        this.firstTeam_Id = firstTeam_Id;

    }

    public int getMatchNo() {
        return matchNo;
    }

    public void setMatchNo(int matchNo) {
        this.matchNo = matchNo;
    }

    public String getFirstTeam_Id() {
        return firstTeam_Id;
    }

    public void setFirstTeam_Id(String firstTeam_Id) {
        this.firstTeam_Id = firstTeam_Id;
    }
    /*
    private String Fixture_Id;

    private String Fixture_Date;

    private String Fixture_Day;

    private String Fixture_Hour;

    private Teams Teams;

    private Teams Teams2;

    private Leagues Leagues;

    private Referee Referee;

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
    */


/*
    public FavMatches(String Fixture_Id, String Fixture_Date, String Fixture_Day, String Fixture_Hour, Teams teams,Teams teams2,Leagues leagues,Referee referee) {
        this.Fixture_Id = Fixture_Id;
        this.Fixture_Date = Fixture_Date;
        this.Fixture_Day = Fixture_Day;
        this.Fixture_Hour = Fixture_Hour;
        this.teams = teams;
        this.teams2 = teams;
    }
*/

}
