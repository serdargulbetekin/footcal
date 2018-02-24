package com.sgfootcal.android.footcal.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.sgfjcrmpr.android.foot_cal.R;
import com.sgfootcal.android.footcal.Adapter.LeaguesAdapter;
import com.sgfootcal.android.footcal.Internet.ApiUtils;
import com.sgfootcal.android.footcal.Internet.LeaguesDaoInterface;
import com.sgfootcal.android.footcal.pojomodel.Leagues;
import com.sgfootcal.android.footcal.pojomodel.LeaguesSample;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by serdar on 23.8.2017.
 */

public class LeaguesActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private Toolbar toolbar;
    private RecyclerView rv;
    private LeaguesAdapter leaguesAdapter;
    private LeaguesDaoInterface leaguesDaoInterface;
    private CoordinatorLayout coordinatorLayout;
    private static int backButtonCount=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.leagues_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbarLeaguesActivity);
        coordinatorLayout=(CoordinatorLayout) findViewById(R.id.CoordinatorLayoutLeaguesActivity);

        toolbar.setTitle("Foot-Cal(Leagues)");
        toolbar.setTitleTextColor(getResources().getColor(R.color.White));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        rv = (RecyclerView) findViewById(R.id.LeaguesActivtyRV);

        rv.setHasFixedSize(true);

        rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rv.setItemAnimator(new DefaultItemAnimator());
        leaguesDaoInterface = ApiUtils.getLeaguesDaoInterface();

        getAllLeagues();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_leagues_search, menu);

        final MenuItem item = menu.findItem(R.id.action_search_seasons);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Search Leagues...");

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (isOnline()) {
            searchAllSeasons(newText);
        }
        return false;
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(LeaguesActivity.this,MainActivity.class));
    }
    public void searchAllSeasons(final String searchKey){

        leaguesDaoInterface.searchAllLeagues(searchKey).enqueue(new Callback<LeaguesSample>() {
            @Override
            public void onResponse(Call<LeaguesSample> call, Response<LeaguesSample> response) {
                if (response.isSuccessful()){
                    List<Leagues> soundsList  = response.body().getLeagues();

                    leaguesAdapter = new LeaguesAdapter(LeaguesActivity.this,soundsList,leaguesDaoInterface);
                    rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                    rv.setItemAnimator(new DefaultItemAnimator());
                    rv.setAdapter(leaguesAdapter);
                }
            }

            @Override
            public void onFailure(Call<LeaguesSample> call, Throwable t) {

            }
        });
    }

    public void getAllLeagues(){

        leaguesDaoInterface.allLeagues().enqueue(new Callback<LeaguesSample>() {
            @Override
            public void onResponse(Call<LeaguesSample> call, Response<LeaguesSample> response) {

                if (response.isSuccessful()){
                    List<Leagues> leaguesList  = response.body().getLeagues();

                    leaguesAdapter = new LeaguesAdapter(LeaguesActivity.this,leaguesList,leaguesDaoInterface);

                    rv.setAdapter(leaguesAdapter);

                }



            }

            @Override
            public void onFailure(Call<LeaguesSample> call, Throwable t) {

            }
        });

    }
    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
           snackBar();
            // Toast.makeText(this, "No Internet connection!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    public void snackBar(){
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "İnternet Yok!", Snackbar.LENGTH_LONG)
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
