package com.sgfootcal.android.footcal.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sgfjcrmpr.android.foot_cal.R;
import com.sgfootcal.android.footcal.Internet.FootballerDaoInterface;
import com.sgfootcal.android.footcal.pojomodel.Fixture;
import com.sgfootcal.android.footcal.pojomodel.Footballer;

import java.util.List;



public class FootballerSquadAdapter extends RecyclerView.Adapter<FootballerSquadAdapter.CardViewTasarimNesneleriniTutucu> {

    private Context mContext;
    private List<Footballer> footballerList;
    private List<Fixture> fixtureListFav;
    private  NotificationCompat.Builder notificationCreater ;
    private SharedPreferences mSharedPrefs;
    private FootballerDaoInterface footballerDaoInterface;
    static int countExistenceControl=0;
    private static final int EMPTY_VIEW = 10;

    public FootballerSquadAdapter(Context mContext, List<Footballer> footballers) {
        this.mContext = mContext;
        this.footballerList = footballers;
    }

    public class CardViewTasarimNesneleriniTutucu extends RecyclerView.ViewHolder {
        public TextView textViewPosition,textViewGoal,textViewAssists,textViewFootballerName,textViewAssistsFootballer,textViewGoalFootballer,textViewShirtNumber;
        public ImageView imageViewTeams;
        public CardView cardViewGoalOrAssist;

        public CardViewTasarimNesneleriniTutucu(View view) {
            super(view);

            textViewPosition = (TextView) view.findViewById(R.id.textViewPosition);
            textViewAssistsFootballer = (TextView) view.findViewById(R.id.textViewAssistsFootballer);
            textViewGoalFootballer = (TextView) view.findViewById(R.id.textViewGoalFootballer);
            textViewShirtNumber = (TextView) view.findViewById(R.id.textViewShirtNumber);
            textViewFootballerName = (TextView) view.findViewById(R.id.textViewFootballerName);
            cardViewGoalOrAssist = (CardView) view.findViewById(R.id.cardViewGoalOrAssist);
        }
    }

    @Override
    public CardViewTasarimNesneleriniTutucu onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_footballer_squad, parent, false);

        return new CardViewTasarimNesneleriniTutucu(itemView);
    }


    @Override
    public void onBindViewHolder(final CardViewTasarimNesneleriniTutucu holder, final int position) {
        final Footballer footballer = footballerList.get(position);

        holder.textViewFootballerName.setText(footballer.getFootballer_Name());
        holder.textViewShirtNumber.setText(String.valueOf(footballer.getFootballer_ShirtNumber() + " - "));
        holder.textViewGoalFootballer.setText("G"+String.valueOf(footballer.getGoalScore_Id()) );
        holder.textViewAssistsFootballer.setText("A"+String.valueOf(footballer.getAssistScore_Id()));
        holder.textViewPosition.setText(footballer.getFootballer_Position());





        holder.cardViewGoalOrAssist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showPopupWindow(holder.cardViewTeams, position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return footballerList.size();
    }
}

