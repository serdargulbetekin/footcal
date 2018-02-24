package com.sgfootcal.android.footcal.pojomodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by serdar on 20.8.2017.
 */

public class CountrySample {
    @SerializedName("Country")
    @Expose
    private List<Country> county = null;
    @SerializedName("success")
    @Expose
    private Integer success;

    public List<Country> getCountry() {
        return county;
    }

    public void setCountry(List<Country> county) {
        this.county = county;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }
}
