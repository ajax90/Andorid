package com.uncc.footballsportsapp.adapters;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.uncc.footballsportsapp.model.Games;
import com.uncc.footballsportsapp.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.uncc.footballsportsapp.fragments.ScoreBoardFragment;
import com.uncc.footballsportsapp.model.Teams;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> {

    private List<Games> lList = Collections.emptyList();
    private List<Teams> tList;

    public void setNewList(List<Games> list) {
        this.lList = list;
        notifyDataSetChanged();
    }


    public CardAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_card, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CardAdapter.MyViewHolder holder, int position) {

        final Games games = lList.get(position);
        Teams homeTeam = null;
        Teams awayTeam = null;

        if (tList != null) {
            Log.d("Inside on create ScoreBoard", "" + tList.size());
            homeTeam = tList.stream().filter(obj -> obj.getTeamName().equals(games.getHomeTeam())).findFirst().orElse(null);
            awayTeam = tList.stream().filter(obj -> obj.getTeamName().equals(games.getAwayTeam())).findFirst().orElse(null);
        }

        if (games.getEventDate() == "null") {
            holder.textViewDate.setVisibility(View.INVISIBLE);
        } else {
            holder.textViewDate.setText(games.getEventDate());
            holder.textViewDate.setVisibility(View.VISIBLE);
        }

        if (games.getLocalTime() == "null") {
            holder.textViewTime.setVisibility(View.INVISIBLE);
        } else {
            holder.textViewTime.setText(games.getLocalTime());
            holder.textViewTime.setVisibility(View.VISIBLE);
        }

        if (games.getHomeTeam() == "null") {
            holder.textViewHomeTeamName.setVisibility(View.INVISIBLE);
        } else {
            holder.textViewHomeTeamName.setText(games.getHomeTeam());
            holder.textViewHomeTeamName.setVisibility(View.VISIBLE);
        }

        if (games.getAwayTeam() == "null") {
            holder.textViewAwayTeamName.setVisibility(View.INVISIBLE);
        } else {
            holder.textViewAwayTeamName.setText(games.getAwayTeam());
            holder.textViewAwayTeamName.setVisibility(View.VISIBLE);
        }

        if (games.getHomeScore() == "null") {
            holder.textViewHomeTeamScore.setVisibility(View.INVISIBLE);
        } else {
            holder.textViewHomeTeamScore.setText(games.getHomeScore());
            holder.textViewHomeTeamScore.setVisibility(View.VISIBLE);
        }

        if (games.getAwayScore() == "null") {
            holder.textViewAwayTeamScore.setVisibility(View.INVISIBLE);
        } else {
            holder.textViewAwayTeamScore.setText(games.getAwayScore());
            holder.textViewAwayTeamScore.setVisibility(View.VISIBLE);
        }

        if (games.getPostpone().equals("yes")) {
            holder.textViewPostpone.setVisibility(View.VISIBLE);
        } else {
            holder.textViewPostpone.setVisibility(View.INVISIBLE);
        }

        if (homeTeam == null) {
            holder.logoHomeTeam.setVisibility(View.INVISIBLE);
        } else {
            Picasso.get().load(homeTeam.getBadge()).into(holder.logoHomeTeam);
            holder.logoHomeTeam.setVisibility(View.VISIBLE);
        }

        if (awayTeam == null) {
            holder.logoAwayTeam.setVisibility(View.INVISIBLE);
        } else {
            Picasso.get().load(awayTeam.getBadge()).into(holder.logoAwayTeam);
            holder.logoAwayTeam.setVisibility(View.VISIBLE);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), "clicked on card", Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putSerializable("GAMES_ITEM_KEY", games);
                bundle.putSerializable("TEAMS_LIST", (Serializable) tList);
                ScoreBoardFragment sFragment = new ScoreBoardFragment();
                sFragment.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity) v.getContext();

                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.layoutContainer, sFragment, "SCOREBOARDFRAGMENT")
                        .addToBackStack(null)
                        .commit();

            }
        });


    }

    @Override
    public int getItemCount() {
        return lList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTime;
        TextView textViewDate;
        TextView textViewAwayTeamScore;
        TextView textViewHomeTeamScore;
        TextView textViewAwayTeamName;
        TextView textViewHomeTeamName;
        TextView textViewPostpone;
        ImageView logoHomeTeam;
        ImageView logoAwayTeam;
        View mView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTime = itemView.findViewById(R.id.textViewTimeScoreCard);
            textViewDate = itemView.findViewById(R.id.textViewDateScoreCard);
            textViewAwayTeamScore = itemView.findViewById(R.id.textViewAwayTeamScoreScoreCard);
            textViewHomeTeamScore = itemView.findViewById(R.id.textViewHomeTeamScoreScoreCard);
            textViewAwayTeamName = itemView.findViewById(R.id.textViewAwayTeamNameScoreCard);
            textViewHomeTeamName = itemView.findViewById(R.id.textViewHomeTeamNameScoreCard);
            textViewPostpone = itemView.findViewById(R.id.textViewPosponedScoreCard);
            logoHomeTeam = itemView.findViewById(R.id.imageViewHomeTeamLogoScoreCard);
            logoAwayTeam = itemView.findViewById(R.id.imageViewAwayTeamLogoScoreCard);

            mView = itemView;
        }
    }

    public void setTeamsList(ArrayList<Teams> teamsListl) {
        tList = teamsListl;
        Log.d("teamListNowHere", "" + tList.size());
    }
}
