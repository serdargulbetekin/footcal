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
    private String Footballer_Id;
    @SerializedName("isOwnGoal")
    @Expose
    private Teams isOwnGoal;


    public String getFixture_Id() {
        return Fixture_Id;
    }

    public void setFixture_Id(String fixture_Id) {
        Fixture_Id = fixture_Id;
    }

    public String getFootballer_Id() {
        return Footballer_Id;
    }

    public void setFootballer_Id(String footballer_Id) {
        Footballer_Id = footballer_Id;
    }

    public Teams getIsOwnGoal() {
        return isOwnGoal;
    }

    public void setIsOwnGoal(Teams isOwnGoal) {
        this.isOwnGoal = isOwnGoal;
    }
}
