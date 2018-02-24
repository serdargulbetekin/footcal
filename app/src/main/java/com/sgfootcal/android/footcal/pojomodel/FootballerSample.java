
package com.sgfootcal.android.footcal.pojomodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FootballerSample {

    @SerializedName("Footballer")
    @Expose
    private List<Footballer> footballers = null;
    @SerializedName("success")
    @Expose
    private Integer success;

    public List<Footballer> getFootballer() {
        return footballers;
    }

    public void setTeams(List<Footballer> footballers) {
        this.footballers = footballers;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

}
