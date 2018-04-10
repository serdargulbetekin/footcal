package com.sgfootcal.android.footcal.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.sgfjcrmpr.android.foot_cal.R;
import com.sgfootcal.android.footcal.Adapter.FavoriteFixtureAdapter;
import com.sgfootcal.android.footcal.Internet.ApiUtils;
import com.sgfootcal.android.footcal.Internet.FixtureDaoInterface;
import com.sgfootcal.android.footcal.pojomodel.Fixture;
import com.sgfootcal.android.footcal.pojomodel.FixtureSample;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by serdar on 26.8.2017.
 */

public class Favorites extends AppCompatActivity {

    private Toolbar toolbar;
    private RelativeLayout relativeLayout;
    private RecyclerView rv;
    private FixtureDaoInterface fixtureDaoInterface;
    private FavoriteFixtureAdapter fixtureAdapter;

    private AdView mAdView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.favorites_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar_favorites_details);
        relativeLayout=(RelativeLayout) findViewById(R.id.CoordinatorLayoutFavoritesActivity);


        toolbar.setTitle("Favorilerim");
        toolbar.setTitleTextColor(getResources().getColor(R.color.White));

        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

TextView textView =(TextView)  toolbar.findViewById(R.id.title);
        textView.setText("Favorilerim");

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (isOnline()) {

            rv = (RecyclerView) findViewById(R.id.recycler_view_favorite_fixture);

            rv.setHasFixedSize(true);

            rv.setLayoutManager(new LinearLayoutManager(this));
            rv.setItemAnimator(new DefaultItemAnimator());
            fixtureDaoInterface = ApiUtils.getFixtureDaoInterface();
            getAllFavFixture();
        }
    }

    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            snackBar();
            //Toast.makeText(this, "No Internet connection!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Favorites.this,MainActivity.class));
    }


    public void getAllFavFixture(){

        fixtureDaoInterface.getAllFavFixture().enqueue(new Callback<FixtureSample>() {
            @Override
            public void onResponse(Call<FixtureSample> call, Response<FixtureSample> response) {

                if (response.isSuccessful()){
                    List<Fixture> favFixture  = response.body().getFixtures();

                    fixtureAdapter = new FavoriteFixtureAdapter(Favorites.this,favFixture,fixtureDaoInterface);
                    rv.setAdapter(fixtureAdapter);

                }

            }

            @Override
            public void onFailure(Call<FixtureSample> call, Throwable t) {

            }
        });
    }
    public void searchAllFavFixtures(final String searchKey){

        fixtureDaoInterface.searchAllFixtureFav(searchKey).enqueue(new Callback<FixtureSample>() {
            @Override
            public void onResponse(Call<FixtureSample> call, Response<FixtureSample> response) {
                if (response.isSuccessful()){
                    List<Fixture> fixtureList  = response.body().getFixtures();

                    fixtureAdapter = new FavoriteFixtureAdapter(Favorites.this,fixtureList,fixtureDaoInterface);
                    rv.setAdapter(fixtureAdapter);
                }
            }

            @Override
            public void onFailure(Call<FixtureSample> call, Throwable t) {

            }
        });
    }
    public void snackBar(){
        Snackbar snackbar = Snackbar
                .make(relativeLayout, "Internet Bağlantısı Yok!", Snackbar.LENGTH_LONG)
                .setAction("Yeniden Dene", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });

        // Mesaj yazısının rengini değiştirme
        snackbar.setActionTextColor(Color.RED);

        // Action button rengini değiştirme.
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);

        snackbar.show();
    }

}
