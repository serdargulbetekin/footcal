package com.sgfootcal.android.footcal.pojomodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by serdar on 18.8.2017.
 */

public class Goals implements Serializable {


    @SerializedName("Fixture_Id")
    @Expose
    private String Fixture_Id;
    @SerializedName("Footballer_Id")
    @Expose
    private Footballer footballer;
    @SerializedName("isOwnGoal")
    @Expose
    private String isOwnGoal;
    @SerializedName("minute")
    @Expose
    private String minute;

    public String getFixture_Id() {
        return Fixture_Id;
    }

    public void setFixture_Id(String fixture_Id) {
        Fixture_Id = fixture_Id;
    }

    public Footballer getFootballer() {
        return footballer;
    }

    public void setFootballer(Footballer footballer) {
        this.footballer = footballer;
    }

    public String getIsOwnGoal() {
        return isOwnGoal;
    }

    public void setIsOwnGoal(String isOwnGoal) {
        this.isOwnGoal = isOwnGoal;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }
}
