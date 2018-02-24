package com.sgfootcal.android.footcal.Internet;


import com.sgfootcal.android.footcal.pojomodel.RefereeSample;
import com.sgfootcal.android.footcal.pojomodel.TeamsSample;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface RefereeDaoInterfae {

    @POST("allReferees.php")
    @FormUrlEncoded
    Call<RefereeSample> allReferees(@Field("Referee_Id") int Referee_Id);
    //Hem veri gönder hem veri çek
    //Field sadece encoded ile kullanılır!!!!

    @POST("allTeamsSearchByLeagueId.php")
    @FormUrlEncoded
    Call<TeamsSample> searchAllTeamsByLeagueId(@Field("Team_Name") String Team_Name,
                                               @Field("League_Id") int League_Id);
}
