
package com.sgfootcal.android.footcal.pojomodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GoalsSample {

    @SerializedName("Goals")
    @Expose
    private List<Goals> goals = null;
    @SerializedName("success")
    @Expose
    private Integer success;

    public List<Goals> getGoals() {
        return goals;
    }

    public void setTeams(List<Goals> goals) {
        this.goals = goals;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

}
