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

import com.sgfjcrmpr.android.foot_cal.R;
import com.sgfootcal.android.footcal.Adapter.PointTableAdapter;
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

public class PointTableFragments extends Fragment implements SearchView.OnQueryTextListener{
    private RecyclerView recyclerView;
    private PointTableAdapter pointTableAdapter;
    private TeamsDaoInterfae teamsDaoInterfae;
    private SharedPreferences mSharedPrefs;
    private Leagues leagues;


    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.point_table,container,false);

        if (isOnline()) {



            setHasOptionsMenu(true);

            teamsDaoInterfae = ApiUtils.getTeamsDaoInterface();

            recyclerView = (RecyclerView) view.findViewById(R.id.PointTableRV);
            recyclerView.setHasFixedSize(true);

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            getAllTeamsPointsByLeagueId(1);


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
            searchAllFixtureByLeague(newText, 1);
        }
        return false;
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    public void getAllTeamsPointsByLeagueId(int league_Id){

        teamsDaoInterfae.allTeamsByLeagueIdSorted(league_Id).enqueue(new Callback<TeamsSample>() {
            @Override
            public void onResponse(Call<TeamsSample> call, Response<TeamsSample> response) {
                if (response.isSuccessful()){
                    List<Teams> teamsList  = response.body().getTeams();

                    pointTableAdapter = new PointTableAdapter(getContext(),teamsList);

                    recyclerView.setAdapter(pointTableAdapter);

                }
            }

            @Override
            public void onFailure(Call<TeamsSample> call, Throwable t) {

            }
        });


    }



    public void searchAllFixtureByLeague(final String searchKey,int League_Id){

        teamsDaoInterfae.searchAllTeamsSearchByLeagueIdPointTable(searchKey,League_Id).enqueue(new Callback<TeamsSample>() {
            @Override
            public void onResponse(Call<TeamsSample> call, Response<TeamsSample> response) {
                if (response.isSuccessful()){
                    List<Teams> teamsList  = response.body().getTeams();

                    pointTableAdapter = new PointTableAdapter(getContext(),teamsList);
                    recyclerView.setAdapter(pointTableAdapter);
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
