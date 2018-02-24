package com.sgfootcal.android.footcal.pojomodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by serdar on 20.8.2017.
 */

public class CoachSample {
    @SerializedName("Coach")
    @Expose
    private List<Coach> coach = null;
    @SerializedName("success")
    @Expose
    private Integer success;

    public List<Coach> getCoach() {
        return coach;
    }

    public void setCountry(List<Coach> coach) {
        this.coach = coach;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }
}
