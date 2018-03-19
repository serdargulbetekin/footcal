package com.sgfootcal.android.footcal.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sgfjcrmpr.android.foot_cal.R;
import com.sgfootcal.android.footcal.Activities.LeaguesDetailsActivity;
import com.sgfootcal.android.footcal.Activities.TeamsDetailsActivity;
import com.sgfootcal.android.footcal.Internet.ApiUtils;
import com.sgfootcal.android.footcal.Internet.ClubChairmanDaoInterface;
import com.sgfootcal.android.footcal.Internet.CoachDaoInterface;
import com.sgfootcal.android.footcal.Internet.FixtureDaoInterface;
import com.sgfootcal.android.footcal.Internet.FootballerDaoInterface;
import com.sgfootcal.android.footcal.Internet.TeamsDaoInterfae;
import com.sgfootcal.android.footcal.pojomodel.ClubChairman;
import com.sgfootcal.android.footcal.pojomodel.ClubChairmanSample;
import com.sgfootcal.android.footcal.pojomodel.Coach;
import com.sgfootcal.android.footcal.pojomodel.CoachSample;
import com.sgfootcal.android.footcal.pojomodel.Fixture;
import com.sgfootcal.android.footcal.pojomodel.FixtureSample;
import com.sgfootcal.android.footcal.pojomodel.Footballer;
import com.sgfootcal.android.footcal.pojomodel.FootballerSample;
import com.sgfootcal.android.footcal.pojomodel.Teams;
import com.sgfootcal.android.footcal.pojomodel.TeamsSample;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.CardViewTasarimNesneleriniTutucu> {


    private DialogGoalAsistAdapter dialogGoalAsistAdapter;
    private DialogGoalAsistAdapter2 dialogGoalAsistAdapter2;
    private DialogRedYellowAdapter dialogRedYellowAdapter;
    private DialogPointTableAdapter dialogPointTableAdapter;
    private RecyclerView rv;

    private Context mContext;
    private List<Teams> teamsList;
    private List<Teams> teamsListPointTable;
    private SharedPreferences mSharedPrefs;
    private final String URL = "http://api.footcal.net/Photos/";

    private View viewAssist;
   private AlertDialog.Builder builderGoalAssisst;
   private AlertDialog dialogGoalAssists;

    private TextView firstTeamName,secondTeamName,firstTeamR,secondteamR,detailsdate,detailshour,detailsstadium,detailscity,detailsreferee,detailsleague;
    private ImageView firstTeamImage,secondTeamImage,btnCancel;
    private   List<Fixture> fixtureList;
    private   List<Footballer> footballersGoal,footballersAssists,footballerRedYellowCard;
    private FixtureDaoInterface fixtureDaoInterface = ApiUtils.getFixtureDaoInterface();

    private TeamsDaoInterfae teamsDaoInterfae = ApiUtils.getTeamsDaoInterface();
    private FootballerDaoInterface footballerDaoInterface = ApiUtils.getFootballerDaoInterface();

    private List<Coach>coachList;
    private List<ClubChairman>clubChairmenList;

    private CoachDaoInterface coachDaoInterface = ApiUtils.getCoachDaoInterface();

    private ClubChairmanDaoInterface clubChairmanDaoInterface= ApiUtils.getClubChairManDaoInterface() ;

    public TeamsAdapter(Context mContext, List<Teams> teamsList) {
        this.mContext = mContext;
        this.teamsList = teamsList;
    }

    public class CardViewTasarimNesneleriniTutucu extends RecyclerView.ViewHolder {
        public TextView textViewTeamsName;
        public ImageView imageViewTeams,menupopup;
        public CardView cardViewTeams;

        public CardViewTasarimNesneleriniTutucu(View view) {
            super(view);
            textViewTeamsName = (TextView) view.findViewById(R.id.textViewTeamsName);
            imageViewTeams = (ImageView) view.findViewById(R.id.imageViewTeams);
            cardViewTeams = (CardView) view.findViewById(R.id.cardViewTeams);
            menupopup =(ImageView) view.findViewById(R.id.menupopup);
        }
    }

    @Override
    public CardViewTasarimNesneleriniTutucu onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_teams, parent, false);

        return new CardViewTasarimNesneleriniTutucu(itemView);
    }


    @Override
    public void onBindViewHolder(final CardViewTasarimNesneleriniTutucu holder, final int position) {
        final Teams teams = teamsList.get(position);
        holder.textViewTeamsName.setText(String.valueOf(teams.getTeams_Name()));

        String url = URL+ teams.getTeams_Photo();

        Picasso.with(mContext)
                .load(url)
                .into(holder.imageViewTeams);

        holder.menupopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPopupWindow(holder.menupopup, position);
            }
        });

        holder.cardViewTeams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext,TeamsDetailsActivity.class);
                intent.putExtra("Characters2",teams);


                mSharedPrefs =  mContext.getSharedPreferences("Characters2",MODE_PRIVATE);
                SharedPreferences.Editor editor = mSharedPrefs.edit();

                Gson gson = new Gson();
                String json = gson.toJson(teams);
                editor.putString("CharactersObject2", json);
                editor.commit();

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return teamsList.size();
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
        popup.getMenuInflater().inflate(R.menu.menu_teams, popup.getMenu());
        popup.setOnMenuItemClickListener(new TeamsAdapter.MyMenuItemClickListener(position));
        getAllTeamsFixtureByTeamId(Integer.parseInt(teamsList.get(position).getTeam_Id()));

        getAllCoaches(Integer.parseInt(teamsList.get(position).getTeam_Id()));
        getAllClubChairmen(Integer.parseInt(teamsList.get(position).getTeam_Id()));


        popup.show();
        popup.getMenu().getItem(0).setTitle(teamsList.get(position).getTeams_Name());

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

            final Teams teams= teamsList.get(position);


            switch (menuItem.getItemId()) {
                case R.id.gototeam:

                    Intent intent = new Intent(mContext,TeamsDetailsActivity.class);
                    intent.putExtra("Characters2",teams);


                    mSharedPrefs =  mContext.getSharedPreferences("Characters2",MODE_PRIVATE);
                    SharedPreferences.Editor editor = mSharedPrefs.edit();

                    Gson gson = new Gson();
                    String json = gson.toJson(teams);
                    editor.putString("CharactersObject2", json);
                    editor.commit();

                    mContext.startActivity(intent);

                    return true;

                case R.id.goals:



                    viewAssist = LayoutInflater.from(mContext).inflate(R.layout.dialog_goals_assists, null); // no parent

                    builderGoalAssisst = new AlertDialog.Builder(mContext);

                    dialogGoalAssists = builderGoalAssisst.create();

                    rv = (RecyclerView) viewAssist.findViewById(R.id.dialogoalassistrv);


                    rv.setHasFixedSize(true);

                    rv.setLayoutManager(new LinearLayoutManager(mContext));
                    rv.setItemAnimator(new DefaultItemAnimator());
                    allFootballerByTeamIdTopScorer(Integer.parseInt(teamsList.get(position).getTeam_Id()));

                    btnCancel = (ImageView) viewAssist.findViewById(R.id.btnCancel);
                    final  TextView twtitlega = (TextView) viewAssist.findViewById(R.id.twtitlega);

                    twtitlega.setText("~En Çok Gol Atanlar~");




//                    clickPropertiesOfLayout(fixtureList.get(position),true);

                    dialogGoalAssists.setTitle(teams.getTeams_Name());
                    dialogGoalAssists.setIcon(R.drawable.goalassist);
                    dialogGoalAssists.requestWindowFeature(Window.FEATURE_RIGHT_ICON);



//                    btnCancel.setImageResource(R.drawable.close);

                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogGoalAssists.dismiss();
                        }
                    });

                    dialogGoalAssists.setView(viewAssist);
                    dialogGoalAssists.show();



                    return true;

                case R.id.assist:


                     viewAssist = LayoutInflater.from(mContext).inflate(R.layout.dialog_goals_assists, null); // no parent

                builderGoalAssisst = new AlertDialog.Builder(mContext);

                    dialogGoalAssists = builderGoalAssisst.create();

                    rv = (RecyclerView) viewAssist.findViewById(R.id.dialogoalassistrv);


                    rv.setHasFixedSize(true);

                    rv.setLayoutManager(new LinearLayoutManager(mContext));
                    rv.setItemAnimator(new DefaultItemAnimator());

                    allFootballerByTeamIdTopAssistScorer(Integer.parseInt(teamsList.get(position).getTeam_Id()));
                    btnCancel = (ImageView) viewAssist.findViewById(R.id.btnCancel);


//                    clickPropertiesOfLayout(fixtureList.get(position),true);

                    dialogGoalAssists.setTitle(teams.getTeams_Name());
                    dialogGoalAssists.setIcon(R.drawable.goalassist);
                    dialogGoalAssists.requestWindowFeature(Window.FEATURE_RIGHT_ICON);



                  //  btnCancel.setImageResource(R.drawable.close);

                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogGoalAssists.dismiss();
                        }
                    });

                    dialogGoalAssists.setView(viewAssist);
                    dialogGoalAssists.show();




                    return true;

                case R.id.matchofweek:
                    if(!fixtureList.get(0).getFixtureResult().getFirstTeam_Goal().equals("-")){
                        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_details_match, null); // no parent

                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                        final AlertDialog dialog = builder.create();

                        firstTeamR = (TextView) view.findViewById(R.id.firstTeamR);
                        secondteamR= (TextView) view.findViewById(R.id.secondteamR);
                        firstTeamName = (TextView) view.findViewById(R.id.firstTeamName);
                        secondTeamName = (TextView) view.findViewById(R.id.secondTeamName);


                        firstTeamImage = (ImageView) view.findViewById(R.id.firstTeamImage);
                        secondTeamImage = (ImageView) view.findViewById(R.id.secondTeamImage);
                        btnCancel = (ImageView) view.findViewById(R.id.btnCancel);

                        firstTeamR.setText(String.valueOf(fixtureList.get(0).getFixtureResult().getFirstTeam_Goal()));
                        secondteamR.setText(String.valueOf(fixtureList.get(0).getFixtureResult().getSecondTeam_Goal()));

                        firstTeamName.setText(String.valueOf(fixtureList.get(0).getTeams().getTeams_Name()));
                        secondTeamName.setText(String.valueOf(fixtureList.get(0).getTeams2().getTeams_Name()));




                        String url = URL + fixtureList.get(0).getTeams().getTeams_Photo();
                        String url2 = URL + fixtureList.get(0).getTeams2().getTeams_Photo();

                        Picasso.with(mContext)
                                .load(url)
                                .into(firstTeamImage);

                        Picasso.with(mContext)
                                .load(url2)
                                .into(secondTeamImage);

                        clickPropertiesOfLayout(fixtureList.get(0),true);

                        dialog.setTitle(fixtureList.get(0).getTeams().getTeams_Name() + " VS " + fixtureList.get(0).getTeams2().getTeams_Name());
                        ;
                        dialog.setIcon(R.drawable.details);
                        dialog.requestWindowFeature(Window.FEATURE_RIGHT_ICON);




                        btnCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        dialog.setView(view);
                        dialog.show();

                    }
                    else {

                        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_details_match2, null); // no parent

                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                        final AlertDialog dialog = builder.create();

                        firstTeamR = (TextView) view.findViewById(R.id.firstTeamR);
                        secondteamR = (TextView) view.findViewById(R.id.secondteamR);
                        firstTeamName = (TextView) view.findViewById(R.id.firstTeamName);
                        secondTeamName = (TextView) view.findViewById(R.id.secondTeamName);


                        detailsdate = (TextView) view.findViewById(R.id.detailsdate);
                        detailshour = (TextView) view.findViewById(R.id.detailshour);
                        detailsstadium = (TextView) view.findViewById(R.id.detailsstadium);
                        detailscity = (TextView) view.findViewById(R.id.detailscity);
                        detailsreferee = (TextView) view.findViewById(R.id.detailsreferee);
                        detailsleague = (TextView) view.findViewById(R.id.detailsleague);


                        firstTeamImage = (ImageView) view.findViewById(R.id.firstTeamImage);
                        secondTeamImage = (ImageView) view.findViewById(R.id.secondTeamImage);
                        btnCancel = (ImageView) view.findViewById(R.id.btnCancel);

                        firstTeamR.setText(String.valueOf(fixtureList.get(0).getFixtureResult().getFirstTeam_Goal()));
                        secondteamR.setText(String.valueOf(fixtureList.get(0).getFixtureResult().getSecondTeam_Goal()));

                        firstTeamName.setText(String.valueOf(fixtureList.get(0).getTeams().getTeams_Name()));
                        secondTeamName.setText(String.valueOf(fixtureList.get(0).getTeams2().getTeams_Name()));


                        detailsdate.setText(String.valueOf(fixtureList.get(0).getFixture_Date() + ", " + fixtureList.get(0).getFixture_Day()));
                        detailshour.setText(String.valueOf(fixtureList.get(0).getFixture_Hour()));
                        detailsstadium.setText(String.valueOf(fixtureList.get(0).getTeams().getTeams_Staidum()));
                        detailscity.setText(String.valueOf(fixtureList.get(0).getTeams().getTeams_City() + ", " + fixtureList.get(0).getTeams().getTeam_Country().getCountry_Name()));
                        detailsreferee.setText(String.valueOf(fixtureList.get(0).getReferee().getReferee_Name()));
                        detailsleague.setText(String.valueOf(fixtureList.get(0).getLeagues().getLeagues_Name()));

                        clickPropertiesOfLayout(fixtureList.get(0), false);

                        String url = URL + fixtureList.get(0).getTeams().getTeams_Photo();
                        String url2 = URL + fixtureList.get(0).getTeams2().getTeams_Photo();

                        Picasso.with(mContext)
                                .load(url)
                                .into(firstTeamImage);

                        Picasso.with(mContext)
                                .load(url2)
                                .into(secondTeamImage);


                        dialog.setTitle(fixtureList.get(0).getTeams().getTeams_Name() + " VS " + fixtureList.get(0).getTeams2().getTeams_Name());
                        dialog.setIcon(R.drawable.details);
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
                    }
                        return  true;


                case R.id.yellowredcard:

                    viewAssist = LayoutInflater.from(mContext).inflate(R.layout.dialog_goals_assists, null); // no parent

                    builderGoalAssisst = new AlertDialog.Builder(mContext);

                    dialogGoalAssists = builderGoalAssisst.create();

                    rv = (RecyclerView) viewAssist.findViewById(R.id.dialogoalassistrv);


                    rv.setHasFixedSize(true);

                    rv.setLayoutManager(new LinearLayoutManager(mContext));
                    rv.setItemAnimator(new DefaultItemAnimator());
                    footballerDaoInterface = ApiUtils.getFootballerDaoInterface();
                    allFootballerByTeamIdYellowRedCard(Integer.parseInt(teamsList.get(position).getTeam_Id()));

                    btnCancel = (ImageView) viewAssist.findViewById(R.id.btnCancel);
                    final  TextView twtitlegaYRCard = (TextView) viewAssist.findViewById(R.id.twtitlega);

                    twtitlegaYRCard.setText("~Kart İstatistikleri~");




//                    clickPropertiesOfLayout(fixtureList.get(position),true);

                    dialogGoalAssists.setTitle(teams.getTeams_Name());
                    dialogGoalAssists.setIcon(R.drawable.card);
                    dialogGoalAssists.requestWindowFeature(Window.FEATURE_RIGHT_ICON);



//                    btnCancel.setImageResource(R.drawable.close);

                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogGoalAssists.dismiss();
                        }
                    });

                    dialogGoalAssists.setView(viewAssist);
                    dialogGoalAssists.show();




                    return true;

                case R.id.point:

                    getAllTeamsPointsByLeagueId(1);
                    viewAssist = LayoutInflater.from(mContext).inflate(R.layout.dialog_goals_assists, null); // no parent

                    builderGoalAssisst = new AlertDialog.Builder(mContext);

                    dialogGoalAssists = builderGoalAssisst.create();

                    rv = (RecyclerView) viewAssist.findViewById(R.id.dialogoalassistrv);


                    rv.setHasFixedSize(true);

                    rv.setLayoutManager(new LinearLayoutManager(mContext));
                    rv.setItemAnimator(new DefaultItemAnimator());

                    btnCancel = (ImageView) viewAssist.findViewById(R.id.btnCancel);
                    final  TextView twtitlegaPoint = (TextView) viewAssist.findViewById(R.id.twtitlega);

                    twtitlegaPoint.setText("~Puan Durumu~");

                    dialogGoalAssists.setTitle(teams.getTeams_Name());
                    dialogGoalAssists.setIcon(R.drawable.details);
                    dialogGoalAssists.requestWindowFeature(Window.FEATURE_RIGHT_ICON);

                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogGoalAssists.dismiss();
                        }
                    });

                    dialogGoalAssists.setView(viewAssist);
                    dialogGoalAssists.show();




                    return true;

                case R.id.chairmancoach:

                    viewAssist = LayoutInflater.from(mContext).inflate(R.layout.dialog_coach_chairman, null); // no parent

                    builderGoalAssisst = new AlertDialog.Builder(mContext);

                    dialogGoalAssists = builderGoalAssisst.create();



                    btnCancel = (ImageView) viewAssist.findViewById(R.id.btnCancel);
                    final TextView twchairmans = (TextView) viewAssist.findViewById(R.id.twchairmans);
                    final TextView twcoachs = (TextView) viewAssist.findViewById(R.id.twcoachs);



                    twchairmans.setText(clubChairmenList.get(0).getClubChairman_Name());
                    twcoachs.setText(coachList.get(0).getCoach_Name());

                    dialogGoalAssists.setTitle(teams.getTeams_Name());
                    dialogGoalAssists.setIcon(R.drawable.person);
                    dialogGoalAssists.requestWindowFeature(Window.FEATURE_RIGHT_ICON);

                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogGoalAssists.dismiss();
                        }
                    });

                    dialogGoalAssists.setView(viewAssist);
                    dialogGoalAssists.show();

                    return true;

                default:
            }
            return false;
        }

    }
    public void getAllCoaches(int Team_Id){

        coachDaoInterface.allCoachesByTeamId(Team_Id).enqueue(new Callback<CoachSample>() {
            @Override
            public void onResponse(Call<CoachSample> call, Response<CoachSample> response) {
                if (response.isSuccessful()){
                    coachList  = response.body().getCoach();



                }
            }

            @Override
            public void onFailure(Call<CoachSample> call, Throwable t) {

            }
        });


    }
    public void getAllClubChairmen(int Team_Id){

        clubChairmanDaoInterface.allClubChairMenByTeamId(Team_Id).enqueue(new Callback<ClubChairmanSample>() {
            @Override
            public void onResponse(Call<ClubChairmanSample> call, Response<ClubChairmanSample> response) {
                if (response.isSuccessful()){
                    clubChairmenList  = response.body().getClubChairMan();



                }
            }

            @Override
            public void onFailure(Call<ClubChairmanSample> call, Throwable t) {

            }
        });


    }
    public void getAllTeamsFixtureByTeamId(int Team_Id){

        fixtureDaoInterface.allFixtureByTeamId(Team_Id).enqueue(new Callback<FixtureSample>() {
            @Override
            public void onResponse(Call<FixtureSample> call, Response<FixtureSample> response) {
                if (response.isSuccessful()){
                    fixtureList  = response.body().getFixtures();



                }
            }

            @Override
            public void onFailure(Call<FixtureSample> call, Throwable t) {

            }
        });


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

    public  void clickPropertiesOfLayout(final Fixture fixture, boolean matchHeld){



        firstTeamImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });
        secondTeamImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(mContext, TeamsDetailsActivity.class);
                intent3.putExtra("Characters2", fixture.getTeams());


                mSharedPrefs = mContext.getSharedPreferences("Characters2", MODE_PRIVATE);
                SharedPreferences.Editor editor3 = mSharedPrefs.edit();

                Gson gson3 = new Gson();
                String json3 = gson3.toJson(fixture.getTeams2());
                editor3.putString("CharactersObject2", json3);
                editor3.commit();

                mContext.startActivity(intent3);
            }
        });
        firstTeamName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });
        secondTeamName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(mContext, TeamsDetailsActivity.class);
                intent3.putExtra("Characters2", fixture.getTeams());


                mSharedPrefs = mContext.getSharedPreferences("Characters2", MODE_PRIVATE);
                SharedPreferences.Editor editor3 = mSharedPrefs.edit();

                Gson gson3 = new Gson();
                String json3 = gson3.toJson(fixture.getTeams2());
                editor3.putString("CharactersObject2", json3);
                editor3.commit();

                mContext.startActivity(intent3);
            }
        });

        if (!matchHeld) {
            detailsleague.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent3 = new Intent(mContext, LeaguesDetailsActivity.class);
                    intent3.putExtra("Characters2", fixture.getTeams());


                    mSharedPrefs = mContext.getSharedPreferences("Characters2", MODE_PRIVATE);
                    SharedPreferences.Editor editor3 = mSharedPrefs.edit();

                    Gson gson3 = new Gson();
                    String json3 = gson3.toJson(fixture.getLeagues());
                    editor3.putString("CharactersObject2", json3);
                    editor3.commit();

                    mContext.startActivity(intent3);
                }
            });
        }
    }

    public void allFootballerByTeamIdTopAssistScorer(int Team_Id) {

        footballerDaoInterface.allFootballerByTeamIdTopAssistScorer(Team_Id).enqueue(new Callback<FootballerSample>() {
            @Override
            public void onResponse(Call<FootballerSample> call, Response<FootballerSample> response) {
                if (response.isSuccessful()) {

                    footballersAssists = response.body().getFootballer();
                    dialogGoalAsistAdapter2 = new DialogGoalAsistAdapter2(mContext, footballersAssists);
                    rv.setAdapter(dialogGoalAsistAdapter2);

                }
            }

            @Override
            public void onFailure(Call<FootballerSample> call, Throwable t) {

            }
        });


    }
    public void allFootballerByTeamIdYellowRedCard(int Team_Id){

        footballerDaoInterface.allFootballerByTeamIdYellowRedCard(Team_Id).enqueue(new Callback<FootballerSample>() {
            @Override
            public void onResponse(Call<FootballerSample> call, Response<FootballerSample> response) {
                if (response.isSuccessful()){
                    footballerRedYellowCard  = response.body().getFootballer();

                    dialogRedYellowAdapter = new DialogRedYellowAdapter(mContext,footballerRedYellowCard);

                    rv.setAdapter(dialogRedYellowAdapter);

                }
            }

            @Override
            public void onFailure(Call<FootballerSample> call, Throwable t) {

            }
        });


    }
    public void getAllTeamsPointsByLeagueId(int league_Id){

        teamsDaoInterfae.allTeamsByLeagueIdSorted(league_Id).enqueue(new Callback<TeamsSample>() {
            @Override
            public void onResponse(Call<TeamsSample> call, Response<TeamsSample> response) {
                if (response.isSuccessful()){
                   teamsListPointTable  = response.body().getTeams();

                    dialogPointTableAdapter = new DialogPointTableAdapter(mContext,teamsListPointTable);

                    rv.setAdapter(dialogPointTableAdapter);

                }
            }

            @Override
            public void onFailure(Call<TeamsSample> call, Throwable t) {

            }
        });


    }


    public void allFootballerByTeamIdTopScorer(int Team_Id) {

        footballerDaoInterface.allFootballerByTeamIdTopScorer(Team_Id).enqueue(new Callback<FootballerSample>() {
            @Override
            public void onResponse(Call<FootballerSample> call, Response<FootballerSample> response) {
                if (response.isSuccessful()) {
                    footballersGoal = response.body().getFootballer();
                    dialogGoalAsistAdapter = new DialogGoalAsistAdapter(mContext,footballersGoal);

                    rv.setAdapter(dialogGoalAsistAdapter);

                }

            }

            @Override
            public void onFailure(Call<FootballerSample> call, Throwable t) {

            }
        });


    }
    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            Toast.makeText(mContext, "Bu takıma gitmek istiyorsanız,internet bağlantınızı açmanız gerekmektedir!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}

