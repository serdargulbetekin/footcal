package com.sgfootcal.android.footcal.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.sgfjcrmpr.android.foot_cal.R;
import com.sgfootcal.android.footcal.Adapter.FixtureAdapterLeague;
import com.sgfootcal.android.footcal.Internet.ApiUtils;
import com.sgfootcal.android.footcal.Internet.FixtureDaoInterface;
import com.sgfootcal.android.footcal.pojomodel.Fixture;
import com.sgfootcal.android.footcal.pojomodel.FixtureSample;
import com.sgfootcal.android.footcal.pojomodel.Leagues;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by serdar on 25.8.2017.
 */

public class TeamsFixture extends Fragment implements SearchView.OnQueryTextListener{
    private RecyclerView recyclerView;
    private FixtureAdapterLeague fixtureAdapterLeague;
    private FixtureDaoInterface fixtureDaoInterface;
    private SharedPreferences mSharedPrefs;
    private Leagues leagues;
    private AdView mAdView;


    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.teams_fixture,container,false);

        if (isOnline()) {

            setHasOptionsMenu(true);


            fixtureDaoInterface = ApiUtils.getFixtureDaoInterface();

            recyclerView = (RecyclerView) view.findViewById(R.id.TeamsFixtureRV);
            recyclerView.setHasFixedSize(true);

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            getAllTeamsFixtureByLeaguesId(1);
            mAdView = (AdView) view.findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }
        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menufirst, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Maç Ara");
        searchView.setMaxWidth(1000);


        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onQueryTextChange(String newText) {
        if (isOnline()) {
            searchAllFixtureByLeague(newText,1);
        }
        return false;
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    public void getAllTeamsFixtureByLeaguesId(int league_Id){

        fixtureDaoInterface.allFixturesByLeagueId(league_Id).enqueue(new Callback<FixtureSample>() {
            @Override
            public void onResponse(Call<FixtureSample> call, Response<FixtureSample> response) {
                if (response.isSuccessful()){
                    List<Fixture> fixtureList  = response.body().getFixtures();

                    fixtureAdapterLeague = new FixtureAdapterLeague(getContext(),fixtureList);

                    recyclerView.setAdapter(fixtureAdapterLeague);

                }
            }

            @Override
            public void onFailure(Call<FixtureSample> call, Throwable t) {

            }
        });


    }



    public void searchAllFixtureByLeague(final String searchKey,int League_Id){

        fixtureDaoInterface.searchAllFixtureByLeagueId(searchKey,League_Id).enqueue(new Callback<FixtureSample>() {
            @Override
            public void onResponse(Call<FixtureSample> call, Response<FixtureSample> response) {
                if (response.isSuccessful()){
                    List<Fixture> soundsList  = response.body().getFixtures();

                    fixtureAdapterLeague = new FixtureAdapterLeague(getContext(),soundsList);
                    recyclerView.setAdapter(fixtureAdapterLeague);
                }
            }

            @Override
            public void onFailure(Call<FixtureSample> call, Throwable t) {

            }
        });
    }
    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            Toast.makeText(getContext(), "Internet Bağlantızı Kontrol Edin!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
