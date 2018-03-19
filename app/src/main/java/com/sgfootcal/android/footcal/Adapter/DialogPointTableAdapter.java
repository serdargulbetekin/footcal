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
import com.sgfootcal.android.footcal.Activities.TeamsDetailsActivity;
import com.sgfootcal.android.footcal.pojomodel.Teams;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class DialogPointTableAdapter extends RecyclerView.Adapter<DialogPointTableAdapter.CardViewTasarimNesneleriniTutucu> {

    private Context mContext;
    private List<Teams> teamsList;
    private SharedPreferences mSharedPrefs;
    private final String URL = "http://api.footcal.net/Photos/";
    private Teams teams2;
    public DialogPointTableAdapter(Context mContext, List<Teams> teamsList) {
        this.mContext = mContext;
        this.teamsList = teamsList;
    }

    public class CardViewTasarimNesneleriniTutucu extends RecyclerView.ViewHolder {
        public TextView textViewTeamsName,textViewPoint,textViewWeek,textViewGF,textViewGA,textViewAverage,textViewNumber;
        public ImageView imageViewTeams;
        public CardView cardViewTeams;

        public CardViewTasarimNesneleriniTutucu(View view) {
            super(view);
            textViewNumber = (TextView) view.findViewById(R.id.twn1);
            textViewTeamsName = (TextView) view.findViewById(R.id.textViewTeamsName);
            textViewWeek = (TextView) view.findViewById(R.id.textViewTeamsWeek);
            textViewPoint = (TextView) view.findViewById(R.id.textViewTeamsPoint);

            imageViewTeams = (ImageView) view.findViewById(R.id.imageViewTeams);
            cardViewTeams = (CardView) view.findViewById(R.id.cardViewPointTable);
        }
    }

    @Override
    public CardViewTasarimNesneleriniTutucu onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_point_table_dialog, parent, false);

        return new CardViewTasarimNesneleriniTutucu(itemView);
    }


    @Override
    public void onBindViewHolder(final CardViewTasarimNesneleriniTutucu holder, final int position) {
        final Teams teams = teamsList.get(position);

        Gson gson = new Gson();
        mSharedPrefs =  mContext.getSharedPreferences("Characters2",MODE_PRIVATE);
        String json = mSharedPrefs.getString("CharactersObject2", "");
        teams2 = gson.fromJson(json, Teams.class);

        holder.textViewTeamsName.setText(String.valueOf(teams.getTeams_Name()));
        holder.textViewWeek.setText(teams.getTeam_Week());
        holder.textViewPoint.setText(teams.getTeam_Point());
        holder.textViewNumber.setText(teams.getTeams_City() + " - ");

        String url = URL + teams.getTeams_Photo();

        Picasso.with(mContext)
                .load(url)
                .into(holder.imageViewTeams);

        holder.cardViewTeams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!teams.getTeams_Name().equals(teams2.getTeams_Name())){



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
            }
        });
    }

    @Override
    public int getItemCount() {
        return teamsList.size();
    }



}

