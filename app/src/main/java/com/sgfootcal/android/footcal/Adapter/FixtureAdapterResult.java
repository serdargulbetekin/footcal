package com.sgfootcal.android.footcal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sgfjcrmpr.android.foot_cal.R;
import com.sgfootcal.android.footcal.Activities.TeamsDetailsActivity;
import com.sgfootcal.android.footcal.Internet.FixtureDaoInterface;
import com.sgfootcal.android.footcal.pojomodel.Fixture;
import com.sgfootcal.android.footcal.pojomodel.FixtureSample;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class FixtureAdapterResult extends RecyclerView.Adapter<FixtureAdapterResult.CardViewTasarimNesneleriniTutucu> {

    private Context mContext;
    private List<Fixture> fixtureList;
    private List<Fixture> fixtureListFav;
    private  NotificationCompat.Builder notificationCreater ;
    private SharedPreferences mSharedPrefs;
    private FixtureDaoInterface fixtureDaoInterface;
    static int countExistenceControl=0;
    private static final int EMPTY_VIEW = 10;
    private final String URL = "http://api.footcal.net/Photos/";
    private TextView firstTeamName,secondTeamName,firstTeamRR,secondteamRR;
    private ImageView firstTeamImage,secondTeamImage,btnCancel;

    public FixtureAdapterResult(Context mContext, List<Fixture> fixtures) {
        this.mContext = mContext;
        this.fixtureList = fixtures;
    }

    public class CardViewTasarimNesneleriniTutucu extends RecyclerView.ViewHolder {
        public TextView firstTeamR,secondTeamR,textViewTeamsName,textViewTeamsName2;
        public ImageView imageViewTeams,imageViewTeams2;
        public CardView cardViewTeams;

        public CardViewTasarimNesneleriniTutucu(View view) {
            super(view);
            firstTeamR = (TextView) view.findViewById(R.id.firstTeamR);
            secondTeamR = (TextView) view.findViewById(R.id.secondTeamR);

            textViewTeamsName = (TextView) view.findViewById(R.id.textViewTeamsName);
            textViewTeamsName2 = (TextView) view.findViewById(R.id.textViewTeamsName2);
            imageViewTeams = (ImageView) view.findViewById(R.id.imageViewTeams);
            imageViewTeams2 = (ImageView) view.findViewById(R.id.imageViewTeams2);

            cardViewTeams = (CardView) view.findViewById(R.id.cardViewFixture);
        }
    }

    @Override
    public CardViewTasarimNesneleriniTutucu onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_results, parent, false);

        return new CardViewTasarimNesneleriniTutucu(itemView);
    }


    @Override
    public void onBindViewHolder(final CardViewTasarimNesneleriniTutucu holder, final int position) {
        final Fixture fixture = fixtureList.get(position);

        holder.firstTeamR.setText(String.valueOf(fixture.getFixtureResult().getFirstTeam_Goal()));
        holder.secondTeamR.setText(String.valueOf(fixture.getFixtureResult().getSecondTeam_Goal()));

        holder.textViewTeamsName.setText(String.valueOf(fixture.getTeams().getTeams_Name()));
        holder.textViewTeamsName2.setText(String.valueOf(fixture.getTeams2().getTeams_Name()));



        String url = URL + fixture.getTeams().getTeams_Photo();
        String url2 = URL + fixture.getTeams2().getTeams_Photo();
        Picasso.with(mContext)
                .load(url)
                .into(holder.imageViewTeams);

        Picasso.with(mContext)
                .load(url2)
                .into(holder.imageViewTeams2);



        holder.cardViewTeams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(holder.cardViewTeams, position);
/*
                View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_fixture_results_details, null); // no parent

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                final AlertDialog dialog = builder.create();

                firstTeamRR = (TextView) view.findViewById(R.id.firstTeamRR);
                secondteamRR= (TextView) view.findViewById(R.id.secondTeamRR);
                firstTeamName = (TextView) view.findViewById(R.id.firstTeamName);
                secondTeamName = (TextView) view.findViewById(R.id.secondTeamName);


                firstTeamImage = (ImageView) view.findViewById(R.id.firstTeamImage);
                secondTeamImage = (ImageView) view.findViewById(R.id.secondTeamImage);
                btnCancel = (ImageView) view.findViewById(R.id.btnCancel);

                firstTeamRR.setText(String.valueOf(fixture.getFixtureResult().getFirstTeam_Goal()));
                secondteamRR.setText(String.valueOf(fixture.getFixtureResult().getSecondTeam_Goal()));

                firstTeamName.setText(String.valueOf(fixture.getTeams().getTeams_Name()));
                secondTeamName.setText(String.valueOf(fixture.getTeams2().getTeams_Name()));



                String url = URL + fixture.getTeams().getTeams_Photo();
                String url2 = URL + fixture.getTeams2().getTeams_Photo();

                Picasso.with(mContext)
                        .load(url)
                        .into(firstTeamImage);

                Picasso.with(mContext)
                        .load(url2)
                        .into(secondTeamImage);


                dialog.setTitle(fixture.getTeams().getTeams_Name() + " VS " + fixture.getTeams2().getTeams_Name());

                dialog.requestWindowFeature(Window.FEATURE_RIGHT_ICON);



                btnCancel.setImageResource(R.drawable.close);

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.setView(view);
                dialog.show();
                */

            }

        });



    }


    public void addToFavFixture( String Fixture_Date, String Fixture_Day,String Fixture_Hour,int FirstTeam_Id,int SecondTeam_Id,int League_Id,int Referee_Id){

        fixtureDaoInterface.insertToFavFixture(Fixture_Date,Fixture_Day,Fixture_Hour,FirstTeam_Id,SecondTeam_Id,League_Id,Referee_Id).enqueue(new Callback<FixtureSample>() {
            @Override
            public void onResponse(Call<FixtureSample> call, Response<FixtureSample> response) {
                if (response.isSuccessful()){

                }
            }

            @Override
            public void onFailure(Call<FixtureSample> call, Throwable t) {

            }
        });
    }

    public void getAllFavSounds(){


        fixtureDaoInterface.getAllFavFixture().enqueue(new Callback<FixtureSample>() {
            @Override
            public void onResponse(Call<FixtureSample> call, Response<FixtureSample> response) {

                if (response.isSuccessful()){
                    fixtureListFav = response.body().getFixtures();

                }
            }

            @Override
            public void onFailure(Call<FixtureSample> call, Throwable t) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return fixtureList.size();
    }

    private void showPopupWindow(View view,int position) {
        PopupMenu popup = new PopupMenu(mContext, view);
        try {
            Field[] fields = popup.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popup);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        popup.getMenuInflater().inflate(R.menu.menu_fixture_result, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(position));
        popup.getMenu().getItem(0).setTitle(fixtureList.get(position).getTeams().getTeams_Name());
        popup.getMenu().getItem(1).setTitle(fixtureList.get(position).getTeams2().getTeams_Name());

        popup.show();

    }

    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        private int position;

        public MyMenuItemClickListener() {
        }

        public MyMenuItemClickListener(int position) {
            this.position = position;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {

            final Fixture fixture = fixtureList.get(position);


            switch (menuItem.getItemId()) {
                case R.id.firstteam:
                    if(goToTeam()) {
                        Intent intent3 = new Intent(mContext, TeamsDetailsActivity.class);
                        intent3.putExtra("Characters2", fixture.getTeams());


                        mSharedPrefs = mContext.getSharedPreferences("Characters2", MODE_PRIVATE);
                        SharedPreferences.Editor editor3 = mSharedPrefs.edit();

                        Gson gson3 = new Gson();
                        String json3 = gson3.toJson(fixture.getTeams());
                        editor3.putString("CharactersObject2", json3);
                        editor3.commit();

                        mContext.startActivity(intent3);
                    }
                    return true;

                case R.id.secondteam:
                    if(goToTeam()) {
                        Intent intent4 = new Intent(mContext, TeamsDetailsActivity.class);
                        intent4.putExtra("Characters2", fixture.getTeams2());


                        mSharedPrefs = mContext.getSharedPreferences("Characters2", MODE_PRIVATE);
                        SharedPreferences.Editor editor4 = mSharedPrefs.edit();

                        Gson gson4 = new Gson();
                        String json4 = gson4.toJson(fixture.getTeams2());
                        editor4.putString("CharactersObject2", json4);
                        editor4.commit();

                        mContext.startActivity(intent4);
                        return true;
                    }
                default:
            }
            return false;
        }

    }
    public boolean isOnlineForAddingToFavs() {
        ConnectivityManager conMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            Toast.makeText(mContext, "Favorilerime eklemek için internet bağlantınızı açınız.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    public boolean goToLeague() {
        ConnectivityManager conMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            Toast.makeText(mContext, "Lige gitmek için internet bağlantınızı açınız.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    public boolean goToTeam() {
        ConnectivityManager conMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            Toast.makeText(mContext, "Takıma gitmek için internet bağlantınızı açınız.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


}

