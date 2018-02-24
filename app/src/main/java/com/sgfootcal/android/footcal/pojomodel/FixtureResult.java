package com.sgfootcal.android.footcal.pojomodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by serdar on 18.8.2017.
 */

public class FixtureResult implements Serializable {


    @SerializedName("Fixture_Id")
    @Expose
    private String Fixture_Id;
    @SerializedName("FirstTeam_Goal")
    @Expose
    private String FirstTeam_Goal;
    @SerializedName("SecondTeam_Goal")
    @Expose
    private String SecondTeam_Goal;

    public String getFixture_Id() {
        return Fixture_Id;
    }

    public void setFixture_Id(String fixture_Id) {
        Fixture_Id = fixture_Id;
    }

    public String getFirstTeam_Goal() {
        return FirstTeam_Goal;
    }

    public void setFirstTeam_Goal(String firstTeam_Goal) {
        FirstTeam_Goal = firstTeam_Goal;
    }

    public String getSecondTeam_Goal() {
        return SecondTeam_Goal;
    }

    public void setSecondTeam_Goal(String secondTeam_Goal) {
        SecondTeam_Goal = secondTeam_Goal;
    }
}
