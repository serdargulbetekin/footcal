package com.sgfootcal.android.footcal.Internet;


import com.sgfootcal.android.footcal.pojomodel.ClubChairmanSample;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;



public interface ClubChairmanDaoInterface {

    @POST("allClubChairmanByTeamId.php")
    @FormUrlEncoded
    Call<ClubChairmanSample> allClubChairMenByTeamId(@Field("Team_Id") int Team_Id);
    //Hem veri gönder hem veri çek
    //Field sadece encoded ile kullanılır!!!!


}
