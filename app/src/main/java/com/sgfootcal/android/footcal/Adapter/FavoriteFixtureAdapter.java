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
import android.util.Log;
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
import com.sgfootcal.android.footcal.Activities.TeamsDetailsActivity;
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

/**
 * Created by serdar on 6.9.2017.
 */

public class FavoriteFixtureAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mContext;
    private List<Fixture> fixtureFavList;
    private FixtureDaoInterface fixtureDaoInterface;
    private SharedPreferences mSharedPrefs;
    private  NotificationCompat.Builder notificationCreater ;
    private ImageView img;
    private static final int EMPTY_VIEW = 10;
    private final String URL = "http://api.footcal.net/Photos/";


    public FavoriteFixtureAdapter(Context mContext, List<Fixture> fixtureFavList, FixtureDaoInterface fixtureDaoInterface) {
        this.mContext = mContext;
        this.fixtureDaoInterface=fixtureDaoInterface;
        this.fixtureFavList =fixtureFavList;
    }
    public class EmptyViewHolder extends RecyclerView.ViewHolder {

        private TextView textView,textView2,textViewTitle;
        private ImageView imageView,imageView2;


        public EmptyViewHolder(View itemView) {
            super(itemView);

            textView=(TextView) itemView.findViewById(R.id.textViewAdded);
            textView2=(TextView) itemView.findViewById(R.id.textViewChoose);
            textViewTitle=(TextView) itemView.findViewById(R.id.textViewTitle);


        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewDate,textViewDay,textViewHour,textViewTeamsName,textViewTeamsName2;
        public ImageView imageViewTeams,imageViewTeams2;
        public CardView cardViewTeams;


        public ViewHolder(View view) {
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        if (viewType == EMPTY_VIEW) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_favorite_fixture_match_without_item, parent, false);
            EmptyViewHolder evh = new EmptyViewHolder(v);
            return evh;
        }

        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_fixture, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ViewHolder){
            final Fixture fixture = fixtureFavList.get(position);
            final ViewHolder vh = (ViewHolder) holder;
            final String url = URL + fixture.getTeams().getTeams_Photo();
            final String url2 = URL + fixture.getTeams2().getTeams_Photo();


            vh.textViewDate.setText(String.valueOf(fixture.getFixture_Date()));
            vh.textViewDay.setText(String.valueOf(fixture.getFixture_Day()));
            vh.textViewHour.setText(String.valueOf(fixture.getFixture_Hour()));

            vh.textViewTeamsName.setText(String.valueOf(fixture.getTeams().getTeams_Name()));
            vh.textViewTeamsName2.setText(String.valueOf(fixture.getTeams2().getTeams_Name()));

            Picasso.with(mContext)
                    .load(url)
                    .into(vh.imageViewTeams);

            Picasso.with(mContext)
                    .load(url2)
                    .into(vh.imageViewTeams2);

            vh.cardViewTeams.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopupWindow(vh.cardViewTeams, position);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return fixtureFavList.size() > 0 ? fixtureFavList.size() : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (fixtureFavList.size() == 0) {
            return EMPTY_VIEW   ;
        }
        return super.getItemViewType(position);
    }


    public void removeFavFixture(int favSounds_Id){


        fixtureDaoInterface.deleteFromFavFixture(favSounds_Id).enqueue(new Callback<FixtureSample>() {
            @Override
            public void onResponse(Call<FixtureSample> call, Response<FixtureSample> response) {
                if (response.isSuccessful()){
                    getAllFavFixture();

                }
            }

            @Override
            public void onFailure(Call<FixtureSample> call, Throwable t) {

            }
        });
    }

    public void getAllFavFixture(){

        fixtureDaoInterface.getAllFavFixture().enqueue(new Callback<FixtureSample>() {
            @Override
            public void onResponse(Call<FixtureSample> call, Response<FixtureSample> response) {

                if (response.isSuccessful()){
                    notifyDataSetChanged();
                    fixtureFavList  = response.body().getFixtures();


                }

            }

            @Override
            public void onFailure(Call<FixtureSample> call, Throwable t) {
                Log.e("Sonuc : ",t.getMessage().toString());
            }
        });


    }
    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            Toast.makeText(mContext, "No Internet connection!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    public boolean isOnlineForDeteingFromFavs() {
        ConnectivityManager conMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            Toast.makeText(mContext, "To delete it from Fav-Sounds,please open your Internet Connection!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }    private void showPopupWindow(View view,int position) {
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
        popup.getMenuInflater().inflate(R.menu.menu_fixture_fav, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(position));

        popup.getMenu().getItem(3).setTitle(fixtureFavList.get(position).getLeagues().getLeagues_Name());
        popup.getMenu().getItem(4).setTitle(fixtureFavList.get(position).getTeams().getTeams_Name());
        popup.getMenu().getItem(5).setTitle(fixtureFavList.get(position).getTeams2().getTeams_Name());
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

            final Fixture fixtureFav = fixtureFavList.get(position);


            switch (menuItem.getItemId()) {
                case R.id.fav:

                    if(isOnlineForDeteingFromFavs()) {

                        Toast.makeText(mContext,
                                fixtureFav.getTeams().getTeams_Name() + "-" + fixtureFav.getTeams2().getTeams_Name() + "Favorilerimden silindi!",
                                Toast.LENGTH_SHORT).show();
                        removeFavFixture(Integer.parseInt(fixtureFav.getFixture_Id()));


                    }

                    return true;


                case R.id.calendar:
                    LayoutInflater inflater = LayoutInflater.from(mContext);

                    View alert_tasarim = inflater.inflate(R.layout.alerttasarimcalendar, null);

                    String url = URL + fixtureFav.getTeams().getTeams_Photo();
                    String url2 = URL + fixtureFav.getTeams2().getTeams_Photo();

                    final TextView textViewTeams1 = (TextView) alert_tasarim.findViewById(R.id.firstteam);
                    final TextView textViewTeams2 = (TextView) alert_tasarim.findViewById(R.id.secondteam);
                    final ImageView imageViewTeams = (ImageView) alert_tasarim.findViewById(R.id.firstteamImage);
                    final ImageView imageViewTeams2 = (ImageView) alert_tasarim.findViewById(R.id.secondteamImage);
                    final EditText editTextExtraMail = (EditText) alert_tasarim.findViewById(R.id.extramail);

                    textViewTeams1.setText(fixtureFav.getTeams().getTeams_Name());
                    textViewTeams2.setText(fixtureFav.getTeams2().getTeams_Name());
                    Picasso.with(mContext)
                            .load(url)
                            .into(imageViewTeams);

                    Picasso.with(mContext)
                            .load(url2)
                            .into(imageViewTeams2);

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

                    alertDialog.setMessage("Calendar(Takvim) Senkronize");

                    alertDialog.setView(alert_tasarim);

                    alertDialog.setPositiveButton("SYNC", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            String months[] = {"Ocak","Şubat","Mart", "Nisan","Mayıs","Haziran", "Temmuz","Ağustos","Eylül","Ekim","Kasım","Aralık"};

                            String temp = fixtureFav.getFixture_Date().toString();
                            temp = temp.substring(temp.indexOf(' ') + 1 , temp.length());//Ay ve Yıl
                            // Toast.makeText(mContext,temp,Toast.LENGTH_SHORT).show();
                            //Toast.makeText(mContext,temp.substring(0,temp.indexOf(' ')),Toast.LENGTH_SHORT).show();
                            String month = temp.substring(0,temp.indexOf(' '));//Ay
                            temp = temp.substring(temp.indexOf(' ') + 1  , temp.length());
                            temp = temp.substring(temp.indexOf(' ') + 1  , temp.length());//Yıl
                            //Toast.makeText(mContext,temp,Toast.LENGTH_SHORT).show();
                            int a =0;

                            for(int i=0 ;i<12;i++){
                                if(months[i].equals(month)){
                                    a=i;
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
                                    Integer.parseInt(fixtureFav.getFixture_Date().substring(0,fixtureFav.getFixture_Date().indexOf(' '))),
                                    Integer.parseInt(fixtureFav.getFixture_Hour().substring(0,2)),
                                    00);

                            long startTime = calInst.getTimeInMillis();

                            Calendar endCal = Calendar.getInstance();
                            endCal.set(2017,
                                    a,
                                    Integer.parseInt(fixtureFav.getFixture_Date().substring(0,fixtureFav.getFixture_Date().indexOf(' '))),
                                    Integer.parseInt(fixtureFav.getFixture_Hour().substring(0,2) + 2),
                                    00);
                            long endTime = endCal.getTimeInMillis();

                            Intent intent = new Intent(Intent.ACTION_INSERT);
                            intent.setData(CalendarContract.Events.CONTENT_URI);
                            intent.putExtra(CalendarContract.Calendars.CALENDAR_COLOR, Color.RED);
                            intent.putExtra(CalendarContract.Events.DESCRIPTION,
                                    ("Tarih:" + fixtureFav.getFixture_Date()+
                                            "\nGün:" + fixtureFav.getFixture_Day()+
                                            "\nSaat:" + fixtureFav.getFixture_Hour()+
                                            "\nHakem:" + fixtureFav.getReferee().getReferee_Name()+
                                            "\nLig:" + fixtureFav.getLeagues().getLeagues_Name()));
                            intent.putExtra(CalendarContract.Events.CALENDAR_COLOR,fixtureFav.getFixture_Date());
                            intent.putExtra(CalendarContract.Events.TITLE, fixtureFav.getTeams().getTeams_Name() + " vs " + fixtureFav.getTeams2().getTeams_Name());
                            //intent.putExtra(CalendarContract.Events.ALL_DAY, true);
                            intent.putExtra(CalendarContract.Events.EVENT_LOCATION,fixtureFav.getTeams().getTeams_Staidum() + " " + fixtureFav.getTeams().getTeams_City() + " " + fixtureFav.getTeams().getTeam_Country().getCountry_Name());
                            intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,startTime);
                            intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,endTime);/*
                            intent.putExtra(CalendarContract.Events.STATUS, 1);
                            intent.putExtra(CalendarContract.Events.VISIBLE, 0);
                            intent.putExtra(CalendarContract.Events.HAS_ALARM, 1);*/

                            //intent.putExtra(CalendarContract.Events.DTSTART,fixture.getFixture_Date());
                            //  intent.putExtra(CalendarContract.,fixture.getFixture_Date());
                            ;/*
                            intent.putExtra(
                                    CalendarContract.EXTRA_EVENT_END_TIME,
                                    cal.getTime().getTime() + 600000);*/


                            intent.putExtra( Intent.EXTRA_EMAIL,editTextExtraMail.getText().toString());
                            mContext.startActivity( intent);

                        }
                    });


                    alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            // TODO Auto-generated method stub

                            Toast.makeText(mContext, "İptal Edildi!", Toast.LENGTH_SHORT).show();


                        }
                    });

                    alertDialog.create().show();
                    return true;
                case R.id.details:
                    Intent intent = new Intent(mContext,FixtureMatchDetails.class);
                    intent.putExtra("FixtureDetails",fixtureFav);


                    mSharedPrefs =  mContext.getSharedPreferences("FixtureDetails",MODE_PRIVATE);
                    SharedPreferences.Editor editor = mSharedPrefs.edit();

                    Gson gson = new Gson();
                    String json = gson.toJson(fixtureFav);
                    editor.putString("FixtureDetails2", json);
                    editor.commit();

                    mContext.startActivity(intent);
                    return true;
                case R.id.leagues:
                    Intent intent2 = new Intent(mContext,LeaguesDetailsActivity.class);
                    intent2.putExtra("Characters2",fixtureFav);


                    mSharedPrefs =  mContext.getSharedPreferences("Characters2",MODE_PRIVATE);
                    SharedPreferences.Editor editor2 = mSharedPrefs.edit();

                    Gson gson2 = new Gson();
                    String json2 = gson2.toJson(fixtureFav.getLeagues());
                    editor2.putString("CharactersObject2", json2);
                    editor2.commit();

                    mContext.startActivity(intent2);
                    return true;
                case R.id.firstteam:
                    Intent intent3 = new Intent(mContext,TeamsDetailsActivity.class);
                    intent3.putExtra("Characters2",fixtureFav.getTeams());


                    mSharedPrefs =  mContext.getSharedPreferences("Characters2",MODE_PRIVATE);
                    SharedPreferences.Editor editor3 = mSharedPrefs.edit();

                    Gson gson3 = new Gson();
                    String json3 = gson3.toJson(fixtureFav.getTeams());
                    editor3.putString("CharactersObject2", json3);
                    editor3.commit();

                    mContext.startActivity(intent3);
                    return true;

                case R.id.secondteam:
                    Intent intent4 = new Intent(mContext,TeamsDetailsActivity.class);
                    intent4.putExtra("Characters2",fixtureFav.getTeams2());


                    mSharedPrefs =  mContext.getSharedPreferences("Characters2",MODE_PRIVATE);
                    SharedPreferences.Editor editor4 = mSharedPrefs.edit();

                    Gson gson4 = new Gson();
                    String json4 = gson4.toJson(fixtureFav.getTeams2());
                    editor4.putString("CharactersObject2", json4);
                    editor4.commit();

                    mContext.startActivity(intent4);
                    return true;
                default:
            }
            return false;
        }


    }


}



