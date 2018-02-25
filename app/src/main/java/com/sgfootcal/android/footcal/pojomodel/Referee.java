package com.sgfootcal.android.footcal.pojomodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by serdar on 18.8.2017.
 */

public class Referee implements Serializable {


    @SerializedName("Referee_Id")
    @Expose
    private String Referee_Id;
    @SerializedName("Referee_Name")
    @Expose
    private String Referee_Name;
    @SerializedName("Referee_Cockade")
    @Expose
    private String Referee_Cockade;
    @SerializedName("Country_Id")
    @Expose
    private com.sgfootcal.android.footcal.pojomodel.Country Country;

    public String getReferee_Id() {
        return Referee_Id;
    }

    public void setReferee_Id(String referee_Id) {
        Referee_Id = referee_Id;
    }

    public String getReferee_Name() {
        return Referee_Name;
    }

    public void setReferee_Name(String referee_Name) {
        Referee_Name = referee_Name;
    }

    public String getReferee_Cockade() {
        return Referee_Cockade;
    }

    public void setReferee_Cockade(String referee_Cockade) {
        Referee_Cockade = referee_Cockade;
    }

    public com.sgfootcal.android.footcal.pojomodel.Country getCountry() {
        return Country;
    }

    public void setCountry(com.sgfootcal.android.footcal.pojomodel.Country country) {
        Country = country;
    }
}
