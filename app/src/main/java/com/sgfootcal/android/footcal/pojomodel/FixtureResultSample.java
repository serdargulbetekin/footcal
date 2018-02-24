package com.sgfootcal.android.footcal.pojomodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by serdar on 20.8.2017.
 */

public class FixtureResultSample {
    @SerializedName("Result")
    @Expose
    private List<FixtureResult> fixtureResults = null;
    @SerializedName("success")
    @Expose
    private Integer success;

    public List<FixtureResult> getFixtureResults() {
        return fixtureResults;
    }

    public void setCountry(List<ClubChairman> clubChairmen) {
        this.fixtureResults = fixtureResults;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }
}
