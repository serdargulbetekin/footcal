package com.sgfootcal.android.footcal.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sgfjcrmpr.android.foot_cal.R;
import com.sgfootcal.android.footcal.Adapter.TeamsAdapter;
import com.sgfootcal.android.footcal.Adapter.ViewPagerAdapter;
import com.sgfootcal.android.footcal.Fragments.AssistFragmentSpecial;
import com.sgfootcal.android.footcal.Fragments.FootballerFragment;
import com.sgfootcal.android.footcal.Fragments.RedYellowCardFragmentSpecial;
import com.sgfootcal.android.footcal.Fragments.TeamsFixtureSpecial;
import com.sgfootcal.android.footcal.Fragments.TopScorerFragmentSpecial;
import com.sgfootcal.android.footcal.Internet.ApiUtils;
import com.sgfootcal.android.footcal.Internet.TeamsDaoInterfae;
import com.sgfootcal.android.footcal.pojomodel.Teams;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by serdar on 22.8.2017.
 */

public class TeamsDetailsActivity extends AppCompatActivity {
    private RecyclerView rv;
    private TeamsAdapter teamsAdapter;
    private Teams teams;
    private Toolbar toolbar;
    private SharedPreferences mSharedPrefs;
    private CoordinatorLayout coordinatorLayout;
    private TeamsDaoInterfae teamsDaoInterfae;
    private CircleImageView imageView;
    private String url;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TextView titleLeague;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teams_activity_details);

        toolbar = (Toolbar) findViewById(R.id.toolbar_teams);
        tabLayout=(TabLayout)findViewById(R.id.tablayout);
        viewPager=(ViewPager)findViewById(R.id.viewpager);
        coordinatorLayout=(CoordinatorLayout) findViewById(R.id.CoordinatorLayoutSeasonsDetailsActivity);
        imageView=(CircleImageView) toolbar.findViewById(R.id.imageViewSymbol);

        titleLeague= (TextView) toolbar.findViewById(R.id.titleteam);


        Gson gson = new Gson();
        mSharedPrefs =  getSharedPreferences("Characters2",MODE_PRIVATE);
        String json = mSharedPrefs.getString("CharactersObject2", "");
        teams = gson.fromJson(json, Teams.class);

        titleLeague.setText(teams.getTeams_Name());

        setSupportActionBar(toolbar);

        url = "http://www.kilincglobal.net/Project/Photos/" + teams.getTeams_Photo();

        teamsDaoInterfae = ApiUtils.getTeamsDaoInterface();

        Picasso.with(this)
                .load(url)
                .into(imageView);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new FootballerFragment(), "Kadrolar");
        viewPagerAdapter.addFragment(new TeamsFixtureSpecial(), "Fikstür");
        viewPagerAdapter.addFragment(new TopScorerFragmentSpecial(), "Gol Krallığı");
        viewPagerAdapter.addFragment(new AssistFragmentSpecial(), "Asist Krallığı");
        viewPagerAdapter.addFragment(new RedYellowCardFragmentSpecial(), "İstatistikler");


        viewPager.setAdapter(viewPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

    }



    @Override
    public void onBackPressed() {
        startActivity(new Intent(TeamsDetailsActivity.this,MainActivity.class));
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



