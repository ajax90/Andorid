package com.uncc.footballsportsapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.uncc.footballsportsapp.model.Leagues;
import com.uncc.footballsportsapp.R;
import com.uncc.footballsportsapp.adapters.TeamAdapter;
import com.uncc.footballsportsapp.model.Teams;

import java.util.ArrayList;

import com.uncc.footballsportsapp.utils.TeamsSourceDownloader;

public class TeamsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Teams> teamList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    Teams teams;
    Spinner spinner;
    Bundle bundle;

    public TeamsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getView().findViewById(R.id.buttonGamesTeams).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GamesFragment gamesFragment = new GamesFragment();
                gamesFragment.setArguments(GamesFragment.bundle);
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.layoutContainer, gamesFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });


        getView().findViewById(R.id.buttonFavorite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FavoritesFragment favoritesFragment = new FavoritesFragment();
                favoritesFragment.setArguments(GamesFragment.bundle);
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.layoutContainer, favoritesFragment)
                        .addToBackStack("TEAMS_FRAGMENT")
                        .commit();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_teams, container, false);
        spinner = (Spinner) view.findViewById(R.id.spinnerLeaguesTeams);

        recyclerView = (RecyclerView) view.findViewById(R.id.layoutRecyclerTeams);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        TeamAdapter teamAdapter = new TeamAdapter();
        mAdapter = teamAdapter;
        recyclerView.setAdapter(mAdapter);

        bundle = getArguments();
        Leagues leagues = (Leagues) bundle.getSerializable("TEAM_LEAGUE");
        ArrayList<String> specleagues = (ArrayList<String>) bundle.getSerializable("SPEC_LEAGUE");
        arrayAdapter = (ArrayAdapter<String>) new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, specleagues);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String leagueName = parent.getItemAtPosition(position).toString();
                new TeamsSourceDownloader((TeamAdapter) mAdapter, leagueName).execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onTeamsFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onTeamsFragmentInteraction();
    }
}