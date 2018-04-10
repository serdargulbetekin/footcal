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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.sgfjcrmpr.android.foot_cal.R;
import com.sgfootcal.android.footcal.Adapter.FixtureAdapterResult;
import com.sgfootcal.android.footcal.Internet.ApiUtils;
import com.sgfootcal.android.footcal.Internet.FixtureDaoInterface;
import com.sgfootcal.android.footcal.pojomodel.Fixture;
import com.sgfootcal.android.footcal.pojomodel.FixtureSample;
import com.sgfootcal.android.footcal.pojomodel.Leagues;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by serdar on 25.8.2017.
 */

public class ResultsFragment extends Fragment implements SearchView.OnQueryTextListener{
    private RecyclerView recyclerView;
    private FixtureAdapterResult fixtureAdapterResult;
    private FixtureDaoInterface fixtureDaoInterface;
    private SharedPreferences mSharedPrefs;
    private Leagues leagues;
    private AdView mAdView;


    private ArrayList<String> weeks = new ArrayList<>();
    private ArrayAdapter<String> veriAdaptoru;
    private Spinner spinnerweeks;





    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.result_fixture,container,false);

        if (isOnline()) {

            setHasOptionsMenu(true);

            fixtureDaoInterface = ApiUtils.getFixtureDaoInterface();

            recyclerView = (RecyclerView) view.findViewById(R.id.TeamsFixtureRV);
            spinnerweeks = (Spinner) view.findViewById(R.id.spinner_weeks);

            recyclerView.setHasFixedSize(true);

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            weeks();

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
        searchView.setQueryHint("Search");
        searchView.setMaxWidth(1000);


        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onQueryTextChange(String newText) {
        if (isOnline()) {
            allFixtureResultSearchByLeagueId(newText, 1);
        }
        return false;
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    public void getAllTeamsFixtureByLeaguesId(int Week){

        fixtureDaoInterface.allFixtureByWeekId(Week).enqueue(new Callback<FixtureSample>() {
            @Override
            public void onResponse(Call<FixtureSample> call, Response<FixtureSample> response) {
                if (response.isSuccessful()){
                    List<Fixture> fixtureList  = response.body().getFixtures();

                    fixtureAdapterResult = new FixtureAdapterResult(getContext(),fixtureList);

                    recyclerView.setAdapter(fixtureAdapterResult);

                }
            }

            @Override
            public void onFailure(Call<FixtureSample> call, Throwable t) {

            }
        });


    }


    public void weeks(){
        weeks.add("28");
        weeks.add("29");
        weeks.add("30");
        weeks.add("31");
        weeks.add("32");
        weeks.add("33");
        weeks.add("34");

        veriAdaptoru=new ArrayAdapter<String>
                (getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, weeks);


        spinnerweeks.setAdapter(veriAdaptoru);



        spinnerweeks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //allFixtureByWeekId(Integer.parseInt(weeks.get(spinnerweeks.getSelectedItemPosition())));
                getAllTeamsFixtureByLeaguesId(Integer.parseInt(weeks.get(spinnerweeks.getSelectedItemPosition())));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void allFixtureResultSearchByLeagueId(final String searchKey,int League_Id){

        fixtureDaoInterface.allFixtureResultSearchByLeagueId(searchKey,League_Id).enqueue(new Callback<FixtureSample>() {
            @Override
            public void onResponse(Call<FixtureSample> call, Response<FixtureSample> response) {
                if (response.isSuccessful()){
                    List<Fixture> soundsList  = response.body().getFixtures();

                    fixtureAdapterResult = new FixtureAdapterResult(getContext(),soundsList);
                    recyclerView.setAdapter(fixtureAdapterResult);
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
