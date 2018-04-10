package com.sgfootcal.android.footcal.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
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
import com.sgfootcal.android.footcal.Adapter.TeamsAdapter;
import com.sgfootcal.android.footcal.Internet.ApiUtils;
import com.sgfootcal.android.footcal.Internet.TeamsDaoInterfae;
import com.sgfootcal.android.footcal.pojomodel.Leagues;
import com.sgfootcal.android.footcal.pojomodel.Teams;
import com.sgfootcal.android.footcal.pojomodel.TeamsSample;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by serdar on 25.8.2017.
 */

public class TeamsFragment extends Fragment implements SearchView.OnQueryTextListener{
    private RecyclerView recyclerView;
    private TeamsAdapter teamsAdapter;
    private TeamsDaoInterfae teamsDaoInterfae;
    private SharedPreferences mSharedPrefs;
    private Leagues leagues;
    private AdView mAdView;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.teams_fragment,container,false);

        if (isOnline()) {

            setHasOptionsMenu(true);


            teamsDaoInterfae = ApiUtils.getTeamsDaoInterface();

            recyclerView = (RecyclerView) view.findViewById(R.id.TeamsFragmentRV);
            recyclerView.setHasFixedSize(true);

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            getAllTeams();
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
        searchView.setQueryHint("Takım Ara");
        searchView.setMaxWidth(1000);


        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onQueryTextChange(String newText) {
        if (isOnline()) {
            searchAllTeams(newText, 1);
        }
        return false;
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
    public void getAllTeams(){

        teamsDaoInterfae.allTeams().enqueue(new Callback<TeamsSample>() {
            @Override
            public void onResponse(Call<TeamsSample> call, Response<TeamsSample> response) {
                if (response.isSuccessful()){
                    List<Teams> teamsList  = response.body().getTeams();

                    teamsAdapter = new TeamsAdapter(getContext(),teamsList);

                    recyclerView.setAdapter(teamsAdapter);

                }
            }

            @Override
            public void onFailure(Call<TeamsSample> call, Throwable t) {

            }
        });




    }
    public void searchAllTeams(final String searchKey,int League_Id){

        teamsDaoInterfae.searchAllTeamsByLeagueId(searchKey,League_Id).enqueue(new Callback<TeamsSample>() {
            @Override
            public void onResponse(Call<TeamsSample> call, Response<TeamsSample> response) {
                if (response.isSuccessful()){
                    List<Teams> teamsList  = response.body().getTeams();

                    teamsAdapter = new TeamsAdapter(getContext(),teamsList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(teamsAdapter);
                }
            }

            @Override
            public void onFailure(Call<TeamsSample> call, Throwable t) {

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
