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
import com.sgfootcal.android.footcal.Adapter.RedYellowAdapter;
import com.sgfootcal.android.footcal.Internet.ApiUtils;
import com.sgfootcal.android.footcal.Internet.FootballerDaoInterface;
import com.sgfootcal.android.footcal.pojomodel.Footballer;
import com.sgfootcal.android.footcal.pojomodel.FootballerSample;
import com.sgfootcal.android.footcal.pojomodel.Leagues;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by serdar on 25.8.2017.
 */

public class RedYellowCardFragment extends Fragment implements SearchView.OnQueryTextListener{
    private RecyclerView recyclerView;
    private RedYellowAdapter redYellowAdapter;
    private FootballerDaoInterface footballerDaoInterface;
    private SharedPreferences mSharedPrefs;
    private Leagues leagues;
    private AdView mAdView;


    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.top_scorer_rv,container,false);

        if (isOnline()) {


            setHasOptionsMenu(true);


            footballerDaoInterface = ApiUtils.getFootballerDaoInterface();

            recyclerView = (RecyclerView) view.findViewById(R.id.TopScorerRV);
            recyclerView.setHasFixedSize(true);

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            allFootballerByLeagueIdYellowRedCard(1);
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
        searchView.setQueryHint("Futbolcu Ara");
        searchView.setMaxWidth(1000);


        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onQueryTextChange(String newText) {
        if (isOnline()) {
            allFootballerSearchByLeagueIdYellowRedCard(newText);
        }
        return false;
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    public void allFootballerByLeagueIdYellowRedCard(int LeagueId){

        footballerDaoInterface.allFootballerByLeagueIdYellowRedCard(LeagueId).enqueue(new Callback<FootballerSample>() {
            @Override
            public void onResponse(Call<FootballerSample> call, Response<FootballerSample> response) {
                if (response.isSuccessful()){
                    List<Footballer> footballerList  = response.body().getFootballer();

                    redYellowAdapter = new RedYellowAdapter(getContext(),footballerList);

                    recyclerView.setAdapter(redYellowAdapter);

                }
            }

            @Override
            public void onFailure(Call<FootballerSample> call, Throwable t) {

            }
        });


    }



    public void allFootballerSearchByLeagueIdYellowRedCard(final String searchKey ){

        footballerDaoInterface.allFootballerSearchByLeagueIdYellowRedCard(searchKey).enqueue(new Callback<FootballerSample>() {
            @Override
            public void onResponse(Call<FootballerSample> call, Response<FootballerSample> response) {
                if (response.isSuccessful()){
                    List<Footballer> footballerList  = response.body().getFootballer();

                    redYellowAdapter = new RedYellowAdapter(getContext(),footballerList);
                    recyclerView.setAdapter(redYellowAdapter);
                }
            }

            @Override
            public void onFailure(Call<FootballerSample> call, Throwable t) {

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
