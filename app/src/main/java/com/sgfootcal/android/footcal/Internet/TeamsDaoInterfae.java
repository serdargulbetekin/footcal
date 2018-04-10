package com.sgfootcal.android.footcal.Internet;


import com.sgfootcal.android.footcal.pojomodel.TeamsSample;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface TeamsDaoInterfae {

    @GET("allTeams.php")
    Call<TeamsSample> allTeams();
    //Sadece veri çekilecek ise GET metodu kullanılır.

    @POST("allTeamsByLeagueId.php")
    @FormUrlEncoded
    Call<TeamsSample> allTeamsByLeagueId(@Field("League_Id") int League_Id);
    //Hem veri gönder hem veri çek
    //Field sadece encoded ile kullanılır!!!!

    @POST("allTeamsByLeagueIdSorted.php")
    @FormUrlEncoded
    Call<TeamsSample> allTeamsByLeagueIdSorted(@Field("League_Id") int League_Id);
    //Hem veri gönder hem veri çek
    //Field sadece encoded ile kullanılır!!!!

    @POST("allTeamsSearchByLeagueId.php")
    @FormUrlEncoded
    Call<TeamsSample> searchAllTeamsByLeagueId(@Field("Team_Name") String Team_Name,
                                               @Field("League_Id") int League_Id);


    @POST("allTeamsSearchByLeagueIdPointTable.php")
    @FormUrlEncoded
    Call<TeamsSample> searchAllTeamsSearchByLeagueIdPointTable(@Field("Team_Name") String Team_Name,
                                                               @Field("League_Id") int League_Id);
}
