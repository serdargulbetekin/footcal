package com.sgfootcal.android.footcal.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.sgfjcrmpr.android.foot_cal.R;
import com.sgfootcal.android.footcal.Adapter.FixtureAdapter;
import com.sgfootcal.android.footcal.Internet.ApiUtils;
import com.sgfootcal.android.footcal.Internet.FixtureDaoInterface;
import com.sgfootcal.android.footcal.pojomodel.Fixture;
import com.sgfootcal.android.footcal.pojomodel.FixtureSample;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView rv;
    private FixtureAdapter fixtureAdapter;
    private FixtureDaoInterface fixtureDaoInterface;
    private static int backButtonCount=0;
    private RelativeLayout coordinatorLayout;
    private FloatingActionButton fabMain;
    private SharedPreferences mSharedPrefs;
    private List<Fixture> fixtureList;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolBarMain);
        coordinatorLayout=(RelativeLayout) findViewById(R.id.CoordinatorLayout);


        toolbar.setTitle("FootCal");

        toolbar.setTitleTextColor(getResources().getColor(R.color.White));
        setSupportActionBar(toolbar);



        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        ImageView imageViewRight = (ImageView) toolbar.findViewById(R.id.imageViewRight);
        ImageView imageViewFavs = (ImageView) toolbar.findViewById(R.id.imageViewFavs);
        TextView textViewApp = (TextView) toolbar.findViewById(R.id.title);



        if (isOnline()){
            rv = (RecyclerView) findViewById(R.id.leagueRV);

            rv.setHasFixedSize(true);

            rv.setLayoutManager(new LinearLayoutManager(this));
            rv.setItemAnimator(new DefaultItemAnimator());

            fixtureDaoInterface = ApiUtils.getFixtureDaoInterface();
            allFixtures();
            textViewApp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                }
            });

            imageViewRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent3 = new Intent(MainActivity.this,LeaguesDetailsActivity.class);
                   /*
                    intent3.putExtra("Characters2",fixtureList.get(0).getLeagues());


                    mSharedPrefs =  getSharedPreferences("Characters2",MODE_PRIVATE);
                    SharedPreferences.Editor editor3 = mSharedPrefs.edit();

                    Gson gson3 = new Gson();
                    String json3 = gson3.toJson(fixtureList.get(0).getLeagues());
                    editor3.putString("CharactersObject2", json3);
                    editor3.commit();
*/
                    startActivity(intent3);

                }
            });
            imageViewFavs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this,Favorites.class));
                }
            });


        }

    }
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_leagues_search, menu);

        final MenuItem item = menu.findItem(R.id.action_search_seasons);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Maç Bul(Ev Sahibi Takım)");
        searchView.setBackgroundColor(Color.RED);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (isOnline()) {
            searchAllFixtures(newText);
        }
            return false;
    }*/
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

        if (backButtonCount >= 1) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            backButtonCount = 0;
        } else {
            Toast.makeText(this, "Uygulamadan Çıkmak İçin Geri Tuşuna Birkez Daha Basın.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }

    }
    /*
    public void searchAllFixtures(final String searchKey){

        fixtureDaoInterface.searchAllFixtures(searchKey).enqueue(new Callback<FixtureSample>() {
            @Override
            public void onResponse(Call<FixtureSample> call, Response<FixtureSample> response) {
                if (response.isSuccessful()){
                    List<Fixture> fixtureList  = response.body().getFixtures();

                    fixtureAdapter = new FixtureAdapter(MainActivity.this,fixtureList);

                    rv.setAdapter(fixtureAdapter);

                }
            }

            @Override
            public void onFailure(Call<FixtureSample> call, Throwable t) {

            }
        });
    }*/

    public void allFixtures(){

        fixtureDaoInterface.allFixtures().enqueue(new Callback<FixtureSample>() {
            @Override
            public void onResponse(Call<FixtureSample> call, Response<FixtureSample> response) {

                if (response.isSuccessful()){
                     fixtureList  = response.body().getFixtures();

                    fixtureAdapter = new FixtureAdapter(MainActivity.this,fixtureList);

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
                .make(coordinatorLayout, "Internet Bağlantısı Yok!", Snackbar.LENGTH_LONG)
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
