package com.uncc.footballsportsapp.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.uncc.footballsportsapp.R;

import java.util.ArrayList;
import java.util.stream.Collectors;

import com.uncc.footballsportsapp.fragments.GamesFragment;
import com.uncc.footballsportsapp.model.Leagues;
import com.uncc.footballsportsapp.model.Sports;

public class SportsAdapter extends RecyclerView.Adapter<SportsAdapter.MyViewHolder> {

    ArrayList<Sports> sList;
    ArrayList<Leagues> leagues;

    public SportsAdapter(ArrayList<Sports> sList, ArrayList<Leagues> leagues) {
        this.sList = sList;
        this.leagues = leagues;
    }


    @NonNull
    @Override
    public SportsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sports_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        final Sports sports = sList.get(position);


        if (sports.getSportName() == "null") {
            holder.textViewSportsName.setVisibility(View.INVISIBLE);
        } else {
            holder.textViewSportsName.setText(sports.getSportName());
            holder.textViewSportsName.setVisibility(View.VISIBLE);
        }

        if (sports.getSportThumbnail() == "null") {
            holder.logo.setVisibility(View.INVISIBLE);
        } else {
            Picasso.get().load(sports.getSportThumbnail()).into(holder.logo);
            holder.logo.setVisibility(View.VISIBLE);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sports.getSportName().equals("Soccer")) {
                    ArrayList<Leagues> specificList = (ArrayList<Leagues>) leagues.stream().filter(obj -> obj.getSportName().equals(sports.getSportName())).collect(Collectors.toList());
                    Bundle bundle = new Bundle();
                    //bundle.putSerializable("SPORTS_ITEM_KEY", sports);
                    bundle.putSerializable("SPECIFIC_LEAGUE_LIST", specificList);
                    GamesFragment gFragment = new GamesFragment();
                    gFragment.setArguments(bundle);
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();

                    activity.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.layoutContainer, gFragment, "FragmentGames")
                            .addToBackStack("SPORTS_FRAGMENT")
                            .commit();
                } else {
                    Toast.makeText(v.getContext(), "Work in progress.\nKindly navigate to soccer.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return sList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewSportsName;
        ImageView logo;
        View mView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewSportsName = itemView.findViewById(R.id.textViewSportsItem);
            logo = itemView.findViewById(R.id.imageViewSportsItem);
            mView = itemView;
        }
    }
}
