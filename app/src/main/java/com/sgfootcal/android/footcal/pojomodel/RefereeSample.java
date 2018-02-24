package com.sgfootcal.android.footcal.pojomodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by serdar on 20.8.2017.
 */

public class RefereeSample {
    @SerializedName("Referee")
    @Expose
    private List<Referee> referees = null;
    @SerializedName("success")
    @Expose
    private Integer success;

    public List<Referee> getReferee() {
        return referees;
    }

    public void setReferee(List<Referee> referees) {
        this.referees = referees;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }
}
