package com.sgfootcal.android.footcal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sgfjcrmpr.android.foot_cal.R;
import com.sgfootcal.android.footcal.Activities.TeamsDetailsActivity;
import com.sgfootcal.android.footcal.pojomodel.Footballer;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class DialogGoalAsistAdapter2 extends RecyclerView.Adapter<DialogGoalAsistAdapter2.CardViewTasarimNesneleriniTutucu> {

    private Context mContext;
    private List<Footballer> footballerList;

    private SharedPreferences mSharedPrefs;
    static int countExistenceControl=0;
    private static final int EMPTY_VIEW = 10;
    private final String URL = "http://api.footcal.net/Photos/";
    public DialogGoalAsistAdapter2(Context mContext, List<Footballer> footballers) {
        this.mContext = mContext;
        this.footballerList = footballers;
    }

    public class CardViewTasarimNesneleriniTutucu extends RecyclerView.ViewHolder {
        public TextView textViewFootballerName,textViewGoalOrAssist,textViewNumber,textViewTeamName;

        public CardView cardViewGoalOrAssist;

        public CardViewTasarimNesneleriniTutucu(View view) {
            super(view);

            textViewNumber = (TextView) view.findViewById(R.id.twn1);
            textViewFootballerName = (TextView) view.findViewById(R.id.twname1);
            textViewGoalOrAssist = (TextView) view.findViewById(R.id.twga1);
            cardViewGoalOrAssist = (CardView) view.findViewById(R.id.cardViewGoalOrAssist);
        }
    }

    @Override
    public CardViewTasarimNesneleriniTutucu onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_dialog_goal_assists, parent, false);

        return new CardViewTasarimNesneleriniTutucu(itemView);
    }


    @Override
    public void onBindViewHolder(final CardViewTasarimNesneleriniTutucu holder, final int position) {
        final Footballer footballer = footballerList.get(position);

        holder.textViewFootballerName.setText(String.valueOf(footballer.getFootballer_Name()));
        holder.textViewGoalOrAssist.setText(String.valueOf(footballer.getAssistScore_Id()));
        holder.textViewNumber.setText(footballer.getFootballer_Id() + " - ");


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
            }
        });


    }

    @Override
    public int getItemCount() {
        return footballerList.size();
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

