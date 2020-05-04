package com.uncc.footballsportsapp.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.uncc.footballsportsapp.R;
import com.uncc.footballsportsapp.SharedPreferencesFavorite;

import java.util.ArrayList;

import com.uncc.footballsportsapp.fragments.TeamInfoFragment;
import com.uncc.footballsportsapp.model.Teams;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MyViewHolder> {
    SharedPreferencesFavorite sharedPreferencesFavorite = new SharedPreferencesFavorite();
    ArrayList<Teams> tList = new ArrayList<>();

    public void setNewTeamList(ArrayList<Teams> list) {
        this.tList = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public FavoriteAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_card, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoriteAdapter.MyViewHolder holder, int position) {

        final Teams team = tList.get(position);


        if (team.getTeamName() == "null") {
            holder.textViewSportsName.setVisibility(View.INVISIBLE);
        } else {
            holder.textViewSportsName.setText(team.getTeamName());
            holder.textViewSportsName.setVisibility(View.VISIBLE);
        }

        if (team.getBadge() == "null") {
            holder.logo.setVisibility(View.INVISIBLE);
        } else {
            Picasso.get().load(team.getBadge()).into(holder.logo);
            holder.logo.setVisibility(View.VISIBLE);
        }

        holder.unfavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Unfavorited..", Toast.LENGTH_SHORT).show();
                sharedPreferencesFavorite.removeFavorite(holder.mView.getContext(), team);
                tList = sharedPreferencesFavorite.getFavorites(holder.mView.getContext());
                setNewTeamList(tList);
            }
        });

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("TEAMS_ITEM_KEY", team);
                TeamInfoFragment teamInfoFragment = new TeamInfoFragment();
                teamInfoFragment.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity) v.getContext();

                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.layoutContainer, teamInfoFragment, "TEAMINFOFRAGMENT")
                        .addToBackStack(null)
                        .commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return tList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewSportsName;
        ImageView logo;
        Button unfavorite;
        View mView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewSportsName = itemView.findViewById(R.id.textViewTeamNameTeamItem);
            logo = itemView.findViewById(R.id.imageViewIconTeamItem);
            unfavorite = itemView.findViewById(R.id.buttonUnFavoriteFavoriteCard);
            mView = itemView;
        }
    }
}
