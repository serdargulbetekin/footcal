package com.sgfootcal.android.footcal.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sgfjcrmpr.android.foot_cal.R;
import com.sgfootcal.android.footcal.Adapter.FixtureAdapter;
import com.sgfootcal.android.footcal.Adapter.OzelSpinnerAdapter;
import com.sgfootcal.android.footcal.Internet.ApiUtils;
import com.sgfootcal.android.footcal.Internet.FixtureDaoInterface;
import com.sgfootcal.android.footcal.Internet.TeamsDaoInterfae;
import com.sgfootcal.android.footcal.pojomodel.Fixture;
import com.sgfootcal.android.footcal.pojomodel.FixtureSample;
import com.sgfootcal.android.footcal.pojomodel.Teams;
import com.sgfootcal.android.footcal.pojomodel.TeamsSample;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {




    private Toolbar toolbar;
    private RecyclerView rv;
    private FixtureAdapter fixtureAdapter;
    private FixtureDaoInterface fixtureDaoInterface;
    private static int backButtonCount=0;
    private RelativeLayout coordinatorLayout;
    private List<Fixture> fixtureList;

    private ArrayList<String> weeks = new ArrayList<>();
    private ArrayAdapter<String> veriAdaptoru;





    private AdView mAdView;

    private FirebaseAuth firebaseAuth;

    private NavigationView navigationView ;
    private DrawerLayout drawer;

    private TeamsDaoInterfae teamsDaoInterfae;
    private List<Teams> teamsList;
    private OzelSpinnerAdapter ozelSpinnerAdapter;
    private Spinner spinnerTeams;
    private SharedPreferences mSharedPrefs;
    private ImageView imageView;

    private TextView textviewweek;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();


        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(MainActivity.this, LoginScreen.class));
        }
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseAuth.getCurrentUser() != null) {
            Toast.makeText(this, "Hoşgeldiniz, " + firebaseUser.getEmail(), Toast.LENGTH_LONG).show();
        }
        toolbar = (Toolbar) findViewById(R.id.toolBarMain);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout_main);
        imageView = (ImageView) findViewById(R.id.imageViewRight);
         textviewweek = (TextView) findViewById(R.id.textviewweek);


        toolbar.setTitle("FootCal");

        toolbar.setTitleTextColor(getResources().getColor(R.color.White));
        setSupportActionBar(toolbar);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, 0, 0);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        View headerView = navigationView.inflateHeaderView(R.layout.nav_image_for_title);//Navigation Drawer için başlık tasarımı yüklenir.
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);


        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        TextView textViewApp = (TextView) toolbar.findViewById(R.id.title);
        spinnerTeams = (Spinner) findViewById(R.id.spinner_weeks);


        if (isOnline()) {

            rv = (RecyclerView) findViewById(R.id.leagueRV);

            rv.setHasFixedSize(true);

            rv.setLayoutManager(new LinearLayoutManager(this));
            rv.setItemAnimator(new DefaultItemAnimator());

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this,LeaguesDetailsActivity.class));
                }
            });

            //teamsDaoInterfae = ApiUtils.getTeamsDaoInterface();
           // allTeams();
            fixtureDaoInterface = ApiUtils.getFixtureDaoInterface();
            weeks();

            textViewApp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                }
            });

        }
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
            (this, android.R.layout.simple_list_item_1, android.R.id.text1, weeks);


    spinnerTeams.setAdapter(veriAdaptoru);



    spinnerTeams.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            allFixtureByWeekId(Integer.parseInt(weeks.get(spinnerTeams.getSelectedItemPosition())));

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });

    }
    public void allTeams() {

        teamsDaoInterfae.allTeams().enqueue(new Callback<TeamsSample>() {
            @Override
            public void onResponse(Call<TeamsSample> call, Response<TeamsSample> response) {
                if (response.isSuccessful()) {
                    teamsList = response.body().getTeams();


                    ozelSpinnerAdapter = new OzelSpinnerAdapter(MainActivity.this, teamsList);
                    spinnerTeams.setAdapter(ozelSpinnerAdapter);

                }

            }

            @Override
            public void onFailure(Call<TeamsSample> call, Throwable t) {

            }
        });


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
    public void alertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle("Çıkış Ekranı");
        builder.setMessage("Çıkış yapmak istediğinizden emin misiniz?");
        builder.setIcon(R.drawable.left);

        builder.setNegativeButton("HAYIR", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                dialog.dismiss();


            }
        });

        builder.setPositiveButton("EVET", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(getApplicationContext(),LoginScreen.class));
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
    public void allFixtureByWeekId(int Week){

        fixtureDaoInterface.allFixtureByWeekId(Week).enqueue(new Callback<FixtureSample>() {
            @Override
            public void onResponse(Call<FixtureSample> call, Response<FixtureSample> response) {

                if (response.isSuccessful()){
                     fixtureList  = response.body().getFixtures();
//                    textviewweek.setText(fixtureList.get(0).getFixture_Week()+ ".Hafta ");


                    fixtureAdapter = new FixtureAdapter(MainActivity.this,fixtureList);

                    rv.setAdapter(fixtureAdapter);

                }


            }

            @Override
            public void onFailure(Call<FixtureSample> call, Throwable t) {

            }
        });

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        int id = item.getItemId();

        switch (id){

            case R.id.favNav:
                Intent intent = new Intent(MainActivity.this,Favorites.class);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
                return true;

            case R.id.leagueNav:
                Intent intent2 = new Intent(MainActivity.this,LeaguesDetailsActivity.class);
                startActivity(intent2);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.logoutNav:
                alertDialog();
                drawer.closeDrawer(GravityCompat.START);
                return true;





        }
        return true;
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
