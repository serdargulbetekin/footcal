package com.sgfootcal.android.footcal.Internet;


import com.sgfootcal.android.footcal.pojomodel.LeaguesSample;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;



public interface LeaguesDaoInterface {

    @GET("allLeagues.php")
    Call<LeaguesSample> allLeagues();
    //Sadece veri çekilecek ise GET metodu kullanılır.

    @POST("allLeaguesSearch.php")
    @FormUrlEncoded
    Call<LeaguesSample> searchAllLeagues(@Field("League_Name") String League_Name);
}
