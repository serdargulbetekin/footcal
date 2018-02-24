package com.sgfootcal.android.footcal.pojomodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by serdar on 20.8.2017.
 */

public class LeaguesSample {
    @SerializedName("Leagues")
    @Expose
    private List<Leagues> characters = null;
    @SerializedName("success")
    @Expose
    private Integer success;

    public List<Leagues> getLeagues() {
        return characters;
    }

    public void setCharacters(List<Leagues> characters) {
        this.characters = characters;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }
}
