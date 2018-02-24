package com.sgfootcal.android.footcal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sgfjcrmpr.android.foot_cal.R;
import com.sgfootcal.android.footcal.Activities.LeaguesDetailsActivity;
import com.sgfootcal.android.footcal.Internet.LeaguesDaoInterface;
import com.sgfootcal.android.footcal.pojomodel.Leagues;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by serdar on 25.8.2017.
 */

public class LeaguesAdapter extends RecyclerView.Adapter<LeaguesAdapter.CardViewTasarimNesneleriniTutucu> {
    private Context mContext;
    private List<Leagues> leaguesList;
    private LeaguesDaoInterface soundsDaoInterface;
    private SharedPreferences mSharedPrefs;
    private final String URL = "http://api.footcal.net/Photos/";


    public LeaguesAdapter(Context mContext, List<Leagues> leaguesList, LeaguesDaoInterface soundsDaoInterface) {
            this.mContext = mContext;
        this.leaguesList =leaguesList;
        this.soundsDaoInterface=soundsDaoInterface;
    }

    public class CardViewTasarimNesneleriniTutucu extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public CardView satirCardView;
        public TextView textViewCharacterName;


        public CardViewTasarimNesneleriniTutucu(View view) {
            super(view);
            satirCardView = (CardView) view.findViewById(R.id.cardViewLeagues);
            imageView= (ImageView) view.findViewById(R.id.imageViewLeagues);
            textViewCharacterName=(TextView) view.findViewById(R.id.textViewLeaguesName);

        }
    }

    @Override
    public CardViewTasarimNesneleriniTutucu onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_leagues, parent, false);


        return new CardViewTasarimNesneleriniTutucu(itemView);
    }


    @Override
    public void onBindViewHolder(final CardViewTasarimNesneleriniTutucu holder, final int position) {
        final Leagues leagues = leaguesList.get(position);



        final String url = URL +leagues.getLeagues_Photo();

        holder.textViewCharacterName.setText(leagues.getLeagues_Name());

        holder.satirCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext,LeaguesDetailsActivity.class);
                intent.putExtra("Characters2",leagues);


                mSharedPrefs =  mContext.getSharedPreferences("Characters2",MODE_PRIVATE);
                SharedPreferences.Editor editor = mSharedPrefs.edit();

                Gson gson = new Gson();
                String json = gson.toJson(leagues);
                editor.putString("CharactersObject2", json);
                editor.commit();

                mContext.startActivity(intent);
            }
        });

        Picasso.with(mContext)
                .load(url)
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {

        return leaguesList.size();
    }

}
