package com.sgfootcal.android.footcal.Internet;

import com.sgfootcal.android.footcal.pojomodel.FixtureSample;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface FixtureDaoInterface {


    @POST("allFixtureResultSearchByLeagueId.php")
    @FormUrlEncoded
    Call<FixtureSample> allFixtureResultSearchByLeagueId(@Field("Team_Name") String Team_Name,
                                                         @Field("League_Id") int League_Id);

    //Hem veri gönder hem veri çek
    //Field sadece encoded ile kullanılır!!!!
    @GET("allFixture.php")
    Call<FixtureSample> allFixtures();

    @POST("allFixtureByLeaguesId.php")
    @FormUrlEncoded
    Call<FixtureSample> allFixturesByLeagueId(@Field("League_Id") int League_Id);

    @POST("allFixtureByWeekId.php")
    @FormUrlEncoded
    Call<FixtureSample> allFixtureByWeekId(@Field("Fixture_Week") int Fixture_Week);
    //Hem veri gönder hem veri çek
    //Field sadece encoded ile kullanılır!!!!

    @POST("allFixtureByTeamId.php")
    @FormUrlEncoded
    Call<FixtureSample> allFixtureByTeamId(@Field("Team_Id") int Team_Id);
    //Hem veri gönder hem veri çek
    //Field sadece encoded ile kullanılır!!!!

    @POST("allFixtureByLeaguesIdResult.php")
    @FormUrlEncoded
    Call<FixtureSample> allFixtureByLeaguesIdResult(@Field("League_Id") int League_Id);
    //Hem veri gönder hem veri çek
    //Field sadece encoded ile kullanılır!!!!

    @POST("allFixtureSearch.php")
    @FormUrlEncoded
    Call<FixtureSample> searchAllFixtures(@Field("Team_Name") String Team_Name);
    //Hem veri gönder hem veri çek
    //Field sadece encoded ile kullanılır!!!!


    @POST("allFixtureSearchByLeagueId.php")
    @FormUrlEncoded
    Call<FixtureSample> searchAllFixtureByLeagueId(@Field("Team_Name") String Team_Name,
                                                   @Field("League_Id") int League_Id);

    //-------------------------------------------------------------------------------------------------------

    @GET("allFixtureFav.php")
    Call<FixtureSample> getAllFavFixture();


    @POST("insertFixtureFav.php")
    @FormUrlEncoded
    Call<FixtureSample> insertToFavFixture(@Field("Fixture_Date") String Fixture_Date,
                                           @Field("Fixture_Day") String Fixture_Day,
                                           @Field("Fixture_Hour") String Fixture_Hour,
                                           @Field("FirstTeam_Id") int FirstTeam_Id,
                                           @Field("SecondTeam_Id") int SecondTeam_Id,
                                           @Field("League_Id") int League_Id,
                                           @Field("Referee_Id") int Referee_Id,
                                           @Field("Fixture_Week") int Fixture_Week);

    //hem veri gönder ve kayıt başarılı mesajı al

    @POST("deleteFixtureFav.php")
    @FormUrlEncoded
    Call<FixtureSample> deleteFromFavFixture(@Field("Fixture_Id") int Fixture_Id);
    //hem veri gönder ve kayıt başarılı mesajı al


    @POST("allFixtureFavSearch.php")
    @FormUrlEncoded
    Call<FixtureSample> searchAllFixtureFav(@Field("Team_Name") String Team_Name);
    //Hem veri gönder hem veri çek
    //Field sadece encoded ile kullanılır!!!!
}
