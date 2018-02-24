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


public class GoalScorerAdapterSpecial extends RecyclerView.Adapter<GoalScorerAdapterSpecial.CardViewTasarimNesneleriniTutucu> {

    private Context mContext;
    private List<Footballer> footballerList;
    private List<Fixture> fixtureListFav;
    private  NotificationCompat.Builder notificationCreater ;
    private SharedPreferences mSharedPrefs;
    private FootballerDaoInterface footballerDaoInterface;
    static int countExistenceControl=0;
    private static final int EMPTY_VIEW = 10;

    public GoalScorerAdapterSpecial(Context mContext, List<Footballer> footballers) {
        this.mContext = mContext;
        this.footballerList = footballers;
    }

    public class CardViewTasarimNesneleriniTutucu extends RecyclerView.ViewHolder {
        public TextView textViewFootballerName,textViewGoalOrAssist,textViewNumber;
        public ImageView imageViewTeams;
        public CardView cardViewGoalOrAssist;

        public CardViewTasarimNesneleriniTutucu(View view) {
            super(view);
            textViewNumber = (TextView) view.findViewById(R.id.textViewNumber);
            textViewFootballerName = (TextView) view.findViewById(R.id.textViewFootballerName);
            textViewGoalOrAssist = (TextView) view.findViewById(R.id.textViewGoalOrAssist);
            imageViewTeams = (ImageView) view.findViewById(R.id.imageViewTeams);
            cardViewGoalOrAssist = (CardView) view.findViewById(R.id.cardViewGoalOrAssist);
        }
    }

    @Override
    public CardViewTasarimNesneleriniTutucu onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_footballer_goals_assits_special, parent, false);

        return new CardViewTasarimNesneleriniTutucu(itemView);
    }


    @Override
    public void onBindViewHolder(final CardViewTasarimNesneleriniTutucu holder, final int position) {
        final Footballer footballer = footballerList.get(position);

        holder.textViewFootballerName.setText(String.valueOf(footballer.getFootballer_Name()));
        holder.textViewGoalOrAssist.setText(String.valueOf(footballer.getGoalScore_Id()));
        holder.textViewNumber.setText(footballer.getFootballer_Id() + " - ");




        String url = "http://www.kilincglobal.net/Project/Photos/" + footballer.getTeams().getTeams_Photo();


        Picasso.with(mContext)
                .load(url)
                .into(holder.imageViewTeams);



    }

    @Override
    public int getItemCount() {
        return footballerList.size();
    }
    private void showPopupWindow(View view,int position) {
        PopupMenu popup = new PopupMenu(mContext, view);
        String url = "http://www.kilincglobal.net/Project/Photos/" + footballerList.get(position).getTeams().getTeams_Photo();


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
        popup.getMenuInflater().inflate(R.menu.menu_goal_assit_scorer, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(position));
        popup.getMenu().getItem(0).setTitle(footballerList.get(position).getTeams().getTeams_Name());




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
            Toast.makeText(mContext, "Bu takıma gitmek istiyorsanız,internet bağlantınızı açmanız gerekmektedir!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}

