package com.sgfootcal.android.footcal.Internet;


import com.sgfootcal.android.footcal.pojomodel.FootballerSample;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface FootballerDaoInterface {

    @POST("allFootballerByLeagueIdTopScorer.php")
    @FormUrlEncoded
    Call<FootballerSample> allFootballerByLeagueIdTopScorer(@Field("League_Id") int League_Id);
    //Hem veri gönder hem veri çek
    //Field sadece encoded ile kullanılır!!!!

    @POST("allFootballerByTeamIdTopScorer.php")
    @FormUrlEncoded
    Call<FootballerSample> allFootballerByTeamIdTopScorer(@Field("Team_Id") int Team_Id);
    //Hem veri gönder hem veri çek
    //Field sadece encoded ile kullanılır!!!!

    @POST("allFootballerByTeamIdTopAssistScorer.php")
    @FormUrlEncoded
    Call<FootballerSample> allFootballerByTeamIdTopAssistScorer(@Field("Team_Id") int Team_Id);
    //Hem veri gönder hem veri çek
    //Field sadece encoded ile kullanılır!!!!

    @POST("allFootballerByTeamId.php")
    @FormUrlEncoded
    Call<FootballerSample> allFootballerByTeamId(@Field("Team_Id") int Team_Id);
    //Hem veri gönder hem veri çek
    //Field sadece encoded ile kullanılır!!!!

    @POST("allFootballerByLeagueAssistId.php")
    @FormUrlEncoded
    Call<FootballerSample> allFootballerByAssistId(@Field("League_Id") int League_Id);


    @POST("allFootballerSearchByLeagueId.php")
    @FormUrlEncoded
    Call<FootballerSample> searchAllFootballerByLeagueId(@Field("Footballer_Name") String Footballer_Name,
                                                         @Field("League_Id") int League_Id);

    @POST("allFootballerSearchByLeagueIdAssist.php")
    @FormUrlEncoded
    Call<FootballerSample> allFootballerSearchByLeagueIdAssist(@Field("Footballer_Name") String Footballer_Name,
                                                               @Field("League_Id") int League_Id);



    @POST("allFootballerSearchByTeamId.php")
    @FormUrlEncoded
    Call<FootballerSample> allFootballerSearchByTeamId(@Field("Footballer_Name") String Footballer_Name,
                                                       @Field("Team_Id") int Team_Id);

    @POST("allFootballerSearchByTeamIdTopScorers.php")
    @FormUrlEncoded
    Call<FootballerSample> allFootballerSearchByTeamIdTopScorers(@Field("Footballer_Name") String Footballer_Name,
                                                                 @Field("Team_Id") int Team_Id);

    @POST("allFootballerSearchByTeamIdTopAssisters.php")
    @FormUrlEncoded
    Call<FootballerSample> allFootballerSearchByTeamIdTopAssisters(@Field("Footballer_Name") String Footballer_Name,
                                                                   @Field("Team_Id") int Team_Id);




    @POST("allFootballerSearchByTeamIdYellowRedCard.php")
    @FormUrlEncoded
    Call<FootballerSample> allFootballerSearchByTeamIdYellowRedCard(@Field("Footballer_Name") String Footballer_Name,
                                                                    @Field("Team_Id") int Team_Id);
    @POST("allFootballerByTeamIdYellowRedCard.php")
    @FormUrlEncoded
    Call<FootballerSample> allFootballerByTeamIdYellowRedCard(
            @Field("Team_Id") int Team_Id);




    @POST("allFootballerByLeagueIdYellowRedCard.php")
    @FormUrlEncoded
    Call<FootballerSample> allFootballerByLeagueIdYellowRedCard(@Field("League_Id") int League_Id);
    //Hem veri gönder hem veri çek
    //Field sadece encoded ile kullanılır!!!!


    @POST("allFootballerSearchByLeagueIdYellowRedCard.php")
    @FormUrlEncoded
    Call<FootballerSample> allFootballerSearchByLeagueIdYellowRedCard(@Field("Footballer_Name") String Footballer_Name);

}
