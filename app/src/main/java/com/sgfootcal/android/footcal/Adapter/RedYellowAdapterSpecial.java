package com.sgfootcal.android.footcal.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sgfjcrmpr.android.foot_cal.R;
import com.sgfootcal.android.footcal.Internet.FootballerDaoInterface;
import com.sgfootcal.android.footcal.pojomodel.Fixture;
import com.sgfootcal.android.footcal.pojomodel.Footballer;

import java.util.List;


public class RedYellowAdapterSpecial extends RecyclerView.Adapter<RedYellowAdapterSpecial.CardViewTasarimNesneleriniTutucu> {

    private Context mContext;
    private List<Footballer> footballerList;
    private List<Fixture> fixtureListFav;
    private  NotificationCompat.Builder notificationCreater ;
    private SharedPreferences mSharedPrefs;
    private FootballerDaoInterface footballerDaoInterface;
    static int countExistenceControl=0;
    private static final int EMPTY_VIEW = 10;

    public RedYellowAdapterSpecial(Context mContext, List<Footballer> footballers) {
        this.mContext = mContext;
        this.footballerList = footballers;
    }

    public class CardViewTasarimNesneleriniTutucu extends RecyclerView.ViewHolder {
        public TextView textViewFootballerName,textViewRedCard,textViewYellowCard,textViewNumber;
        public ImageView imageViewYellow,imageVieRed;
        public CardView cardViewGoalOrAssist;

        public CardViewTasarimNesneleriniTutucu(View view) {
            super(view);
            textViewNumber = (TextView) view.findViewById(R.id.textViewShirtNumber);
            textViewFootballerName = (TextView) view.findViewById(R.id.textViewFootballerName);
            textViewRedCard = (TextView) view.findViewById(R.id.textViewRedCard);
            textViewYellowCard = (TextView) view.findViewById(R.id.textViewYellowCard);


            imageViewYellow = (ImageView) view.findViewById(R.id.imageViewYellow);
            imageVieRed = (ImageView) view.findViewById(R.id.imageVieRed);
            cardViewGoalOrAssist = (CardView) view.findViewById(R.id.cardViewYellowOrRedCard);
        }
    }

    @Override
    public CardViewTasarimNesneleriniTutucu onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_footballer_yellow_red_card_special, parent, false);

        return new CardViewTasarimNesneleriniTutucu(itemView);
    }


    @Override
    public void onBindViewHolder(final CardViewTasarimNesneleriniTutucu holder, final int position) {
        final Footballer footballer = footballerList.get(position);

        holder.textViewFootballerName.setText(String.valueOf(footballer.getFootballer_Name()));
        holder.textViewYellowCard.setText( String.valueOf(footballer.getYellowCard()));
        holder.textViewRedCard.setText(String.valueOf(footballer.getRedCard()));
        holder.textViewNumber.setText(footballer.getFootballer_Id() + " - ");




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

