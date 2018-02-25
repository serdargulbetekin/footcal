package com.sgfootcal.android.footcal.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.CalendarContract;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sgfjcrmpr.android.foot_cal.R;
import com.sgfootcal.android.footcal.Activities.FixtureMatchDetails;
import com.sgfootcal.android.footcal.Activities.LeaguesDetailsActivity;
import com.sgfootcal.android.footcal.Internet.ApiUtils;
import com.sgfootcal.android.footcal.Internet.FixtureDaoInterface;
import com.sgfootcal.android.footcal.pojomodel.Fixture;
import com.sgfootcal.android.footcal.pojomodel.FixtureSample;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.sgfjcrmpr.android.foot_cal.R.id.calendar;


public class FixtureAdapterSpecial extends RecyclerView.Adapter<FixtureAdapterSpecial.CardViewTasarimNesneleriniTutucu> {

    private Context mContext;
    private List<Fixture> fixtureList;
    private List<Fixture> fixtureListFav;
    private  NotificationCompat.Builder notificationCreater ;
    private SharedPreferences mSharedPrefs;
    private FixtureDaoInterface fixtureDaoInterface;
    static int countExistenceControl=0;
    private static final int EMPTY_VIEW = 10;
    private final String URL = "http://api.footcal.net/Photos/";
    public FixtureAdapterSpecial(Context mContext, List<Fixture> fixtures) {
        this.mContext = mContext;
        this.fixtureList = fixtures;
    }

    public class CardViewTasarimNesneleriniTutucu extends RecyclerView.ViewHolder {
        public TextView textViewDate,textViewDay,textViewHour,textViewTeamsName,textViewTeamsName2;
        public ImageView imageViewTeams,imageViewTeams2;
        public CardView cardViewTeams;

        public CardViewTasarimNesneleriniTutucu(View view) {
            super(view);
            textViewDate = (TextView) view.findViewById(R.id.textViewDate);
            textViewDay = (TextView) view.findViewById(R.id.textViewDay);
            textViewHour = (TextView) view.findViewById(R.id.textViewHour);
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
                .inflate(R.layout.card_view_fixture, parent, false);

        return new CardViewTasarimNesneleriniTutucu(itemView);
    }


    @Override
    public void onBindViewHolder(final CardViewTasarimNesneleriniTutucu holder, final int position) {
        final Fixture fixture = fixtureList.get(position);

        holder.textViewDate.setText(String.valueOf(fixture.getFixture_Date()));
        holder.textViewDay.setText(String.valueOf(fixture.getFixture_Day()));
        holder.textViewHour.setText(String.valueOf(fixture.getFixture_Hour()));

        holder.textViewTeamsName.setText(String.valueOf(fixture.getTeams().getTeams_Name()));
        holder.textViewTeamsName2.setText(String.valueOf(fixture.getTeams2().getTeams_Name()));

        if (! fixture.getFixtureResult().getFirstTeam_Goal().equals("-")){
            holder.textViewDate.setText(fixture.getFixtureResult().getFirstTeam_Goal());
            holder.textViewDay.setText("-");
            holder.textViewHour.setText(fixture.getFixtureResult().getSecondTeam_Goal());
        }



        String url = URL+ fixture.getTeams().getTeams_Photo();
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
        popup.getMenuInflater().inflate(R.menu.menu_fixture_special, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(position));

        popup.show();
        fixtureDaoInterface= ApiUtils.getFixtureDaoInterface();
        getAllFavSounds();

        popup.getMenu().getItem(3).setTitle(fixtureList.get(position).getLeagues().getLeagues_Name());
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
                case R.id.fav:

                    if (isOnlineForAddingToFavs()) {

                        int favSoundsListOptimization = fixtureListFav.size();

                        for (int i = 0; i < favSoundsListOptimization; i++) {


                            if (fixtureListFav.get(i).getTeams().getTeams_Name().equals(fixture.getTeams().getTeams_Name())) {
                                countExistenceControl++;

                            }
                        }
                        if (countExistenceControl != 0) {
                            Toast.makeText(mContext, fixture.getTeams().getTeams_Name() + "-" +fixture.getTeams2().getTeams_Name() + " zaten Favorilerimde mevcut.", Toast.LENGTH_SHORT).show();

                            countExistenceControl = 0;
                        }
                        else if(!fixture.getFixtureResult().getFirstTeam_Goal().equals("-")){
                            Toast.makeText(mContext, fixture.getTeams().getTeams_Name() + "-" +fixture.getTeams2().getTeams_Name() + " maçı oynandı.Favorilere ekleyemezsiniz.", Toast.LENGTH_SHORT).show();

                        }
                        else {
                            Toast.makeText(mContext, fixture.getTeams().getTeams_Name() + "-" +fixture.getTeams2().getTeams_Name() + " Favorilerime eklendi!", Toast.LENGTH_SHORT).show();

                            addToFavFixture(fixture.getFixture_Date(), fixture.getFixture_Day(), fixture.getFixture_Hour(),
                                    Integer.parseInt(fixture.getTeams().getTeam_Id()), Integer.parseInt(fixture.getTeams2().getTeam_Id()),
                                    Integer.parseInt(fixture.getLeagues().getLeagues_Id()),Integer.parseInt(fixture.getReferee().getReferee_Id()));
                            countExistenceControl = 0;
                        }


                    }

                    return true;


                case calendar:
                    if(!fixture.getFixtureResult().getFirstTeam_Goal().equals("-")){
                        Toast.makeText(mContext, fixture.getTeams().getTeams_Name() + "-" +fixture.getTeams2().getTeams_Name() + " maçı oynandı.Takvim'e ekleyemezsiniz.", Toast.LENGTH_SHORT).show();

                    }
                    else {

                        LayoutInflater inflater = LayoutInflater.from(mContext);

                        View alert_tasarim = inflater.inflate(R.layout.alerttasarimcalendar, null);

                        String url = URL + fixture.getTeams().getTeams_Photo();
                        String url2 = URL + fixture.getTeams2().getTeams_Photo();

                        final TextView textViewTeams1 = (TextView) alert_tasarim.findViewById(R.id.firstteam);
                        final TextView textViewTeams2 = (TextView) alert_tasarim.findViewById(R.id.secondteam);
                        final ImageView imageViewTeams = (ImageView) alert_tasarim.findViewById(R.id.firstteamImage);
                        final ImageView imageViewTeams2 = (ImageView) alert_tasarim.findViewById(R.id.secondteamImage);
                        final EditText editTextExtraMail = (EditText) alert_tasarim.findViewById(R.id.extramail);

                        textViewTeams1.setText(fixture.getTeams().getTeams_Name());
                        textViewTeams2.setText(fixture.getTeams2().getTeams_Name());
                        Picasso.with(mContext)
                                .load(url)
                                .into(imageViewTeams);

                        Picasso.with(mContext)
                                .load(url2)
                                .into(imageViewTeams2);

                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

                        alertDialog.setMessage("Calendar(Takvim)-Sync");

                        alertDialog.setView(alert_tasarim);

                        alertDialog.setPositiveButton("Senkronize Et", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                String months[] = {"Ocak", "Şubat", "Mart", "Nisan", "Mayıs", "Haziran", "Temmuz", "Ağustos", "Eylül", "Ekim", "Kasım", "Aralık"};

                                String temp = fixture.getFixture_Date().toString();
                                temp = temp.substring(temp.indexOf(' ') + 1, temp.length());//Ay ve Yıl
                                // Toast.makeText(mContext,temp,Toast.LENGTH_SHORT).show();
                                //Toast.makeText(mContext,temp.substring(0,temp.indexOf(' ')),Toast.LENGTH_SHORT).show();
                                String month = temp.substring(0, temp.indexOf(' '));//Ay
                                temp = temp.substring(temp.indexOf(' ') + 1, temp.length());
                                temp = temp.substring(temp.indexOf(' ') + 1, temp.length());//Yıl
                                //Toast.makeText(mContext,temp,Toast.LENGTH_SHORT).show();
                                int a = 0;

                                for (int i = 0; i < 12; i++) {
                                    if (months[i].equals(month)) {
                                        a = i;
                                    }
                                }

/*
                            Uri calendarUri = CalendarContract.CONTENT_URI
                                    .buildUpon()
                                    .appendPath("time")
                                    .build();
                            */
/*
                            Toast.makeText(mContext,"Day:" + fixture.getFixture_Date().substring(0,fixture.getFixture_Date().indexOf(' ')),Toast.LENGTH_SHORT).show();
                            Toast.makeText(mContext,"Month:" + Integer.toString(a),Toast.LENGTH_SHORT).show();
                            Toast.makeText(mContext,"Year:" + temp,Toast.LENGTH_SHORT).show();*/
                                Calendar calInst = Calendar.getInstance();

                                calInst.set(Integer.parseInt(temp),
                                        a,
                                        Integer.parseInt(fixture.getFixture_Date().substring(0, fixture.getFixture_Date().indexOf(' '))),
                                        Integer.parseInt(fixture.getFixture_Hour().substring(0, 2)),
                                        00);

                                long startTime = calInst.getTimeInMillis();

                                Calendar endCal = Calendar.getInstance();
                                endCal.set(2017,
                                        a,
                                        Integer.parseInt(fixture.getFixture_Date().substring(0, fixture.getFixture_Date().indexOf(' '))),
                                        Integer.parseInt(fixture.getFixture_Hour().substring(0, 2) + 2),
                                        00);
                                long endTime = endCal.getTimeInMillis();

                                Intent intent = new Intent(Intent.ACTION_INSERT);
                                intent.setData(CalendarContract.Events.CONTENT_URI);
                                intent.putExtra(CalendarContract.Calendars.CALENDAR_COLOR, Color.RED);
                                intent.putExtra(CalendarContract.Events.DESCRIPTION,
                                        ("Tarih:" + fixture.getFixture_Date() +
                                                "\nGün:" + fixture.getFixture_Day() +
                                                "\nSaat:" + fixture.getFixture_Hour() +
                                                "\nHakem:" + fixture.getReferee().getReferee_Name() +
                                                "\nLig:" + fixture.getLeagues().getLeagues_Name()));
                                intent.putExtra(CalendarContract.Events.CALENDAR_COLOR, fixture.getFixture_Date());
                                intent.putExtra(CalendarContract.Events.TITLE, fixture.getTeams().getTeams_Name() + " vs " + fixture.getTeams2().getTeams_Name());
                                //intent.putExtra(CalendarContract.Events.ALL_DAY, true);
                                intent.putExtra(CalendarContract.Events.EVENT_LOCATION, fixture.getTeams().getTeams_Staidum() + " " + fixture.getTeams().getTeams_City() + " " + fixture.getTeams().getTeam_Country().getCountry_Name());
                                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime);
                                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime);/*
                            intent.putExtra(CalendarContract.Events.STATUS, 1);
                            intent.putExtra(CalendarContract.Events.VISIBLE, 0);
                            intent.putExtra(CalendarContract.Events.HAS_ALARM, 1);*/

                                //intent.putExtra(CalendarContract.Events.DTSTART,fixture.getFixture_Date());
                                //  intent.putExtra(CalendarContract.,fixture.getFixture_Date());
                                ;/*
                            intent.putExtra(
                                    CalendarContract.EXTRA_EVENT_END_TIME,
                                    cal.getTime().getTime() + 600000);*/


                                intent.putExtra(Intent.EXTRA_EMAIL, editTextExtraMail.getText().toString());
                                mContext.startActivity(intent);

                            }
                        });


                        alertDialog.setNegativeButton("İptal", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                // TODO Auto-generated method stub

                                Toast.makeText(mContext, "Senkronizasyon iptal edildi!", Toast.LENGTH_SHORT).show();


                            }
                        });

                        alertDialog.create().show();


/*
                    Intent intent = new Intent(mContext, SeasonsDetails.class);
                    intent.putExtra("Characters2", sounds);

                    mSharedPrefs = mContext.getSharedPreferences("Characters2", MODE_PRIVATE);
                    SharedPreferences.Editor editor = mSharedPrefs.edit();

                    Gson gson = new Gson();
                    String json = gson.toJson(sounds);
                    editor.putString("CharactersObject2", json);
                    editor.commit();

                    mContext.startActivity(intent);
*/
                    }
                    return true;
                case R.id.details:
                        Intent intent = new Intent(mContext,FixtureMatchDetails.class);
                intent.putExtra("FixtureDetails",fixture);


                mSharedPrefs =  mContext.getSharedPreferences("FixtureDetails",MODE_PRIVATE);
                SharedPreferences.Editor editor = mSharedPrefs.edit();

                Gson gson = new Gson();
                String json = gson.toJson(fixture);
                editor.putString("FixtureDetails2", json);
                editor.commit();

                mContext.startActivity(intent);
                    return true;
                case R.id.leagues:
                    if(goToLeague()) {
                        Intent intent2 = new Intent(mContext, LeaguesDetailsActivity.class);
                        intent2.putExtra("Characters2", fixture.getLeagues());


                        mSharedPrefs = mContext.getSharedPreferences("Characters2", MODE_PRIVATE);
                        SharedPreferences.Editor editor2 = mSharedPrefs.edit();

                        Gson gson2 = new Gson();
                        String json2 = gson2.toJson(fixture.getLeagues());
                        editor2.putString("CharactersObject2", json2);
                        editor2.commit();

                        mContext.startActivity(intent2);
                    }
                    return true;


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

