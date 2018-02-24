package com.sgfootcal.android.footcal.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sgfjcrmpr.android.foot_cal.R;
import com.sgfootcal.android.footcal.Adapter.FixtureAdapter;
import com.sgfootcal.android.footcal.Internet.ApiUtils;
import com.sgfootcal.android.footcal.Internet.FixtureDaoInterface;
import com.sgfootcal.android.footcal.pojomodel.Fixture;
import com.squareup.picasso.Picasso;

public class FixtureMatchDetails extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView rv;
    private FixtureAdapter fixtureAdapter;
    private FixtureDaoInterface fixtureDaoInterface;
    private SharedPreferences mSharedPrefs;
    private Fixture fixture;
    private TextView textViewLeague,textViewStadium,textViewCity,textViewCountry,textViewHour,textViewDay,textViewDate,textViewTeamsName,textViewTeamsName2,textViewReferee,imageViewLeagueCard;
    private ImageView circleImageLeague,circleImageStadium,circleImageCity,circleImageCountry,circleImageHour,circleImageDay,circleImageDate;
    private ImageView imageViewTeams,imageViewTeams2;
    private String url,url2,url3;
    private TextView titleLeague;
    private final String URL = "http://api.footcal.net/Photos/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixture_details);

        toolbar = (Toolbar) findViewById(R.id.toolBarFixtureDetails);


        toolbar.setTitle("Maç Detayları");
        toolbar.setTitleTextColor(getResources().getColor(R.color.White));
        setSupportActionBar(toolbar);

        titleLeague= (TextView) toolbar.findViewById(R.id.titledetails);


        textViewLeague=(TextView) findViewById(R.id.textViewLeague);
        textViewStadium=(TextView) findViewById(R.id.textViewStadium);
        textViewCity=(TextView) findViewById(R.id.textViewCity);
        textViewCountry=(TextView) findViewById(R.id.textViewCountry);
        textViewHour=(TextView) findViewById(R.id.textViewHour);
        textViewDay=(TextView) findViewById(R.id.textViewDay);
        textViewDate=(TextView) findViewById(R.id.textViewDate);
        textViewTeamsName=(TextView) findViewById(R.id.textViewTeamsName);
        textViewTeamsName2=(TextView) findViewById(R.id.textViewTeamsName2);
        textViewReferee=(TextView) findViewById(R.id.textViewReferee);



      //  circleImageLeague=(CircleImageView) findViewById(R.id.circleImageLeague);
        circleImageStadium=(ImageView) findViewById(R.id.circleImageStadium);
        circleImageCity=(ImageView) findViewById(R.id.circleImageCity);
        circleImageCountry=(ImageView) findViewById(R.id.circleImageCountry);
        circleImageHour=(ImageView) findViewById(R.id.circleImageHour);
        circleImageDay=(ImageView) findViewById(R.id.circleImageDay);
        circleImageDate=(ImageView) findViewById(R.id.circleImageDate);

        imageViewTeams=(ImageView) findViewById(R.id.imageViewTeams);
        imageViewTeams2=(ImageView) findViewById(R.id.imageViewTeams2);
        imageViewLeagueCard=(TextView) findViewById(R.id.imageViewLeagueCard);


        Gson gson = new Gson();
        mSharedPrefs =  getSharedPreferences("FixtureDetails",MODE_PRIVATE);
        String json = mSharedPrefs.getString("FixtureDetails2", "");
        fixture = gson.fromJson(json, Fixture.class);



        url = URL + fixture.getLeagues().getLeagues_Photo();
        url2 = URL + fixture.getTeams().getTeams_Photo();
        url3 = URL + fixture.getTeams2().getTeams_Photo();
        fixtureDaoInterface = ApiUtils.getFixtureDaoInterface();






        textViewLeague.setText(fixture.getLeagues().getLeagues_Name());
        textViewStadium.setText(fixture.getTeams().getTeams_Staidum());
        textViewCity.setText(fixture.getTeams().getTeams_City());
        textViewCountry.setText(fixture.getTeams().getTeam_Country().getCountry_Name());
        textViewHour.setText(fixture.getFixture_Hour());
        textViewDay.setText(fixture.getFixture_Day());
        textViewDate.setText(fixture.getFixture_Date());
        textViewTeamsName.setText(fixture.getTeams().getTeams_Name());
        textViewTeamsName2.setText(fixture.getTeams2().getTeams_Name());
            textViewReferee.setText(fixture.getReferee().getReferee_Name());
/*
        Picasso.with(this)
                .load(url)
                .into(circleImageLeague);*/

        Picasso.with(this)
                .load(url2)
                .into(circleImageStadium);
        /* Picasso.with(this)
                .load(url)
                .into(circleImageCity);
       Picasso.with(this)
                .load(url)
                .into(circleImageCountry);
        Picasso.with(this)
                .load(url)
                .into(circleImageHour);
        Picasso.with(this)
                .load(url)
                .into(circleImageDay);
        Picasso.with(this)
                .load(url)
                .into(circleImageDate);*/

        Picasso.with(this)
                .load(url2)
                .into(imageViewTeams);
        Picasso.with(this)
                .load(url3)
                .into(imageViewTeams2);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        goToTeam();

    }

    @Override
    public void onBackPressed() {

    startActivity(new Intent(FixtureMatchDetails.this,MainActivity.class));

    }
    public void goToTeam(){
        imageViewTeams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(getApplicationContext(),TeamsDetailsActivity.class);
                intent3.putExtra("Characters2",fixture.getTeams());


                mSharedPrefs =  getSharedPreferences("Characters2",MODE_PRIVATE);
                SharedPreferences.Editor editor3 = mSharedPrefs.edit();

                Gson gson3 = new Gson();
                String json3 = gson3.toJson(fixture.getTeams());
                editor3.putString("CharactersObject2", json3);
                editor3.commit();

                startActivity(intent3);
            }
        });
        imageViewTeams2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(getApplicationContext(),TeamsDetailsActivity.class);
                intent3.putExtra("Characters2",fixture.getTeams2());


                mSharedPrefs =  getSharedPreferences("Characters2",MODE_PRIVATE);
                SharedPreferences.Editor editor3 = mSharedPrefs.edit();

                Gson gson3 = new Gson();
                String json3 = gson3.toJson(fixture.getTeams2());
                editor3.putString("CharactersObject2", json3);
                editor3.commit();

                startActivity(intent3);
            }
        });
        imageViewLeagueCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(),LeaguesDetailsActivity.class);
                intent2.putExtra("Characters2",fixture.getLeagues());


                mSharedPrefs =  getSharedPreferences("Characters2",MODE_PRIVATE);
                SharedPreferences.Editor editor2 = mSharedPrefs.edit();

                Gson gson2 = new Gson();
                String json2 = gson2.toJson(fixture.getLeagues());
                editor2.putString("CharactersObject2", json2);
                editor2.commit();

                startActivity(intent2);
            }
        });
    }
}
