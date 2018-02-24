package com.sgfootcal.android.footcal.pojomodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by serdar on 20.8.2017.
 */

public class ClubChairmanSample {
    @SerializedName("ClubChairman")
    @Expose
    private List<ClubChairman> clubChairmen = null;
    @SerializedName("success")
    @Expose
    private Integer success;

    public List<ClubChairman> getClubChairMan() {
        return clubChairmen;
    }

    public void setCountry(List<ClubChairman> clubChairmen) {
        this.clubChairmen = clubChairmen;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }
}
