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
import com.sgfootcal.android.footcal.Internet.FootballerDaoInterface;
import com.sgfootcal.android.footcal.pojomodel.Fixture;
import com.sgfootcal.android.footcal.pojomodel.Footballer;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class RedYellowAdapter extends RecyclerView.Adapter<RedYellowAdapter.CardViewTasarimNesneleriniTutucu> {

    private Context mContext;
    private List<Footballer> footballerList;
    private List<Fixture> fixtureListFav;
    private  NotificationCompat.Builder notificationCreater ;
    private SharedPreferences mSharedPrefs;
    private FootballerDaoInterface footballerDaoInterface;
    static int countExistenceControl=0;
    private static final int EMPTY_VIEW = 10;
    private final String URL = "http://api.footcal.net/Photos/";
    public RedYellowAdapter(Context mContext, List<Footballer> footballers) {
        this.mContext = mContext;
        this.footballerList = footballers;
    }

    public class CardViewTasarimNesneleriniTutucu extends RecyclerView.ViewHolder {
        public TextView textViewFootballerName,textViewRedCard,textViewYellowCard,textViewNumber;
        public ImageView imageViewYellow,imageVieRed,imageViewTeams;
        public CardView cardViewGoalOrAssist;

        public CardViewTasarimNesneleriniTutucu(View view) {
            super(view);
            textViewNumber = (TextView) view.findViewById(R.id.textViewShirtNumber);
            textViewFootballerName = (TextView) view.findViewById(R.id.textViewFootballerName);
            textViewRedCard = (TextView) view.findViewById(R.id.textViewRedCard);
            textViewYellowCard = (TextView) view.findViewById(R.id.textViewYellowCard);

            imageViewTeams = (ImageView) view.findViewById(R.id.imageViewTeams);
            imageViewYellow = (ImageView) view.findViewById(R.id.imageViewYellow);
            imageVieRed = (ImageView) view.findViewById(R.id.imageVieRed);
            cardViewGoalOrAssist = (CardView) view.findViewById(R.id.cardViewYellowOrRedCard);
        }
    }

    @Override
    public CardViewTasarimNesneleriniTutucu onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_footballer_yellow_red_card, parent, false);

        return new CardViewTasarimNesneleriniTutucu(itemView);
    }


    @Override
    public void onBindViewHolder(final CardViewTasarimNesneleriniTutucu holder, final int position) {
        final Footballer footballer = footballerList.get(position);

        holder.textViewFootballerName.setText(String.valueOf(footballer.getFootballer_Name()));
        holder.textViewYellowCard.setText( String.valueOf(footballer.getYellowCard()));
        holder.textViewRedCard.setText(String.valueOf(footballer.getRedCard()));
        holder.textViewNumber.setText(footballer.getFootballer_Id() + " - ");



        String url = URL+ footballer.getTeams().getTeams_Photo();

        Picasso.with(mContext)
                .load(url)
                .into(holder.imageViewTeams);

        holder.cardViewGoalOrAssist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnline()) {

                    Intent intent = new Intent(mContext, TeamsDetailsActivity.class);
                    intent.putExtra("Characters2", footballer.getTeams());

                    mSharedPrefs = mContext.getSharedPreferences("Characters2", MODE_PRIVATE);
                    SharedPreferences.Editor editor = mSharedPrefs.edit();

                    Gson gson = new Gson();
                    String json = gson.toJson(footballer.getTeams());
                    editor.putString("CharactersObject2", json);
                    editor.commit();

                    mContext.startActivity(intent);

                }
               // showPopupWindow(holder.cardViewGoalOrAssist, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return footballerList.size();
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

            final Footballer footballer = footballerList.get(position);


            switch (menuItem.getItemId()) {
                case R.id.teamGoalAssist:
                    if (isOnline()) {

                        Intent intent = new Intent(mContext, TeamsDetailsActivity.class);
                        intent.putExtra("Characters2", footballer.getTeams());

                        mSharedPrefs = mContext.getSharedPreferences("Characters2", MODE_PRIVATE);
                        SharedPreferences.Editor editor = mSharedPrefs.edit();

                        Gson gson = new Gson();
                        String json = gson.toJson(footballer.getTeams());
                        editor.putString("CharactersObject2", json);
                        editor.commit();

                        mContext.startActivity(intent);
                        return true;
                    }
            }
            return false;
        }

    }
    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            Toast.makeText(mContext, "To listen it, please open your Internet Connection!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


}

