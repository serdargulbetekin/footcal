package com.sgfootcal.android.footcal.Internet;


import com.sgfootcal.android.footcal.pojomodel.GoalsSample;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface GoalsDaoInterface {

    @POST("allGoalsByFixtureId.php")
    @FormUrlEncoded
    Call<GoalsSample> allGoalsByFixtureId(@Field("Fixture_Id") int Fixture_Id);
    //Hem veri gönder hem veri çek
    //Field sadece encoded ile kullanılır!!!!


}
