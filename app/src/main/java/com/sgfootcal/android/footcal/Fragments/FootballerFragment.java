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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.sgfjcrmpr.android.foot_cal.R;
import com.sgfootcal.android.footcal.Adapter.FootballerSquadAdapter;
import com.sgfootcal.android.footcal.Internet.ApiUtils;
import com.sgfootcal.android.footcal.Internet.ClubChairmanDaoInterface;
import com.sgfootcal.android.footcal.Internet.CoachDaoInterface;
import com.sgfootcal.android.footcal.Internet.FootballerDaoInterface;
import com.sgfootcal.android.footcal.pojomodel.ClubChairman;
import com.sgfootcal.android.footcal.pojomodel.ClubChairmanSample;
import com.sgfootcal.android.footcal.pojomodel.Coach;
import com.sgfootcal.android.footcal.pojomodel.CoachSample;
import com.sgfootcal.android.footcal.pojomodel.Footballer;
import com.sgfootcal.android.footcal.pojomodel.FootballerSample;
import com.sgfootcal.android.footcal.pojomodel.Teams;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by serdar on 25.8.2017.
 */

public class FootballerFragment extends Fragment implements SearchView.OnQueryTextListener{
    private RecyclerView recyclerView;
    private FootballerSquadAdapter footballerSquadAdapter;
    private FootballerDaoInterface footballerDaoInterface;
    private CoachDaoInterface coachDaoInterface;
    private ClubChairmanDaoInterface clubChairmanDaoInterface;
    private SharedPreferences mSharedPrefs;
    private TextView coachName,chairmanName,textViewCoachName2,textViewChairmanName;
    private Teams teams;
    private AdView mAdView;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.footballer_fragment,container,false);

        if (isOnline()) {




            Gson gson = new Gson();
            mSharedPrefs =  getContext().getSharedPreferences("Characters2",MODE_PRIVATE);
            String json = mSharedPrefs.getString("CharactersObject2", "");
            teams = gson.fromJson(json, Teams.class);
            setHasOptionsMenu(true);

            footballerDaoInterface = ApiUtils.getFootballerDaoInterface();
            coachDaoInterface = ApiUtils.getCoachDaoInterface();
            clubChairmanDaoInterface = ApiUtils.getClubChairManDaoInterface();

           // coachName= (TextView) view.findViewById(R.id.textViewCoachName);
          //  chairmanName= (TextView) view.findViewById(R.id.textViewChairmanName);
          //  textViewCoachName2= (TextView) view.findViewById(R.id.textViewCoachName2);
          //  textViewChairmanName= (TextView) view.findViewById(R.id.textViewChairmanName2);


            recyclerView = (RecyclerView) view.findViewById(R.id.footballerFragmentRV);
            recyclerView.setHasFixedSize(true);

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            allFootballer(Integer.parseInt(teams.getTeam_Id()));
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
            allFootballerSearchByTeamId(newText, Integer.parseInt(teams.getTeam_Id()));
        }
        return false;
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    public void allFootballer(int Team_Id){

        footballerDaoInterface.allFootballerByTeamId(Team_Id).enqueue(new Callback<FootballerSample>() {
            @Override
            public void onResponse(Call<FootballerSample> call, Response<FootballerSample> response) {
                if (response.isSuccessful()){
                    List<Footballer> footballerList  = response.body().getFootballer();

                    footballerSquadAdapter = new FootballerSquadAdapter(getContext(),footballerList);

                    recyclerView.setAdapter(footballerSquadAdapter);

                }

                }

            @Override
            public void onFailure(Call<FootballerSample> call, Throwable t) {

            }
        });


    }
    public void getCoach(int Team_Id){

        coachDaoInterface.allCoachesByTeamId(Team_Id).enqueue(new Callback<CoachSample>() {
            @Override
            public void onResponse(Call<CoachSample> call, Response<CoachSample> response) {
                if (response.isSuccessful()){
                    List<Coach> coachList  = response.body().getCoach();
                    textViewCoachName2.setText( coachList.get(0).getCoach_Name());


                }

            }

            @Override
            public void onFailure(Call<CoachSample> call, Throwable t) {

            }
        });


    }
    public void getClubChairman(int Team_Id){

        clubChairmanDaoInterface.allClubChairMenByTeamId(Team_Id).enqueue(new Callback<ClubChairmanSample>() {
            @Override
            public void onResponse(Call<ClubChairmanSample> call, Response<ClubChairmanSample> response) {
                if (response.isSuccessful()){
                    List<ClubChairman> clubChairmen  = response.body().getClubChairMan();
                    textViewChairmanName.setText( clubChairmen.get(0).getClubChairman_Name());


                }

            }

            @Override
            public void onFailure(Call<ClubChairmanSample> call, Throwable t) {

            }
        });


    }

    public void allFootballerSearchByTeamId(final String searchKey,int Team_Id){

        footballerDaoInterface.allFootballerSearchByTeamId(searchKey,Team_Id).enqueue(new Callback<FootballerSample>() {
            @Override
            public void onResponse(Call<FootballerSample> call, Response<FootballerSample> response) {
                if (response.isSuccessful()){
                    List<Footballer> footballerList  = response.body().getFootballer();

                    footballerSquadAdapter = new FootballerSquadAdapter(getContext(),footballerList);
                    recyclerView.setAdapter(footballerSquadAdapter);
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
