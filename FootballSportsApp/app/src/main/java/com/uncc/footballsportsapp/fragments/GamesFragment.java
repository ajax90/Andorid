package com.uncc.footballsportsapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uncc.footballsportsapp.model.Games;
import com.uncc.footballsportsapp.R;
import com.uncc.footballsportsapp.adapters.CardAdapter;

import java.util.ArrayList;
import java.util.stream.Collectors;

import com.uncc.footballsportsapp.model.Leagues;
import com.uncc.footballsportsapp.utils.GamesSourceDowloader;
import com.uncc.footballsportsapp.utils.TeamsSourceDownloader;

public class GamesFragment extends Fragment {

    // TODO: Rename and change types of parameters
    public static Bundle bundle;
    private String selectedSport;
    private String leaguesUrl;
    ArrayList<Games> gamesListUpcoming = new ArrayList<>();
    ArrayList<Games> gamesListPrevious = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    Spinner spinner;
    ArrayAdapter<String> arrayAdapter;
    String buttonCheck = "upc";
    String leagueIdCheck;
    Leagues specLeague;
    ArrayList<String> leaguesString = new ArrayList<>();
    GamesSourceDowloader gamesSourceDowloader = null;
    TeamsSourceDownloader teamsSourceDownloader = null;

    private OnFragmentInteractionListener mListener;

    public GamesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getView().findViewById(R.id.buttonTeams).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeamsFragment teamsFragment = new TeamsFragment();
                Bundle bundleTeam = new Bundle();
                bundleTeam.putSerializable("TEAM_LEAGUE", specLeague);
                bundleTeam.putSerializable("SPEC_LEAGUE", leaguesString);
                teamsFragment.setArguments(bundleTeam);
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.layoutContainer, teamsFragment)
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
                        .addToBackStack("GAMES_FRAGMENT")
                        .commit();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_games, container, false);
        // Inflate the layout for this fragment
        spinner = (Spinner) view.findViewById(R.id.spinnerLeagues);

        recyclerView = view.findViewById(R.id.layoutRecyclerGames);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        CardAdapter cardAdapter = new CardAdapter();
        mAdapter = cardAdapter;
        recyclerView.setAdapter(mAdapter);

        view.findViewById(R.id.buttonUpcomming).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonCheck = "upc";
                view.findViewById(R.id.buttonUpcomming).setBackgroundResource(R.drawable.my_border);
                view.findViewById(R.id.buttonPrevious).setBackgroundResource(R.color.transparentcolor);
                new GamesSourceDowloader(buttonCheck, leagueIdCheck, cardAdapter).execute();
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
            }
        });

        view.findViewById(R.id.buttonPrevious).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonCheck = "pre";
                view.findViewById(R.id.buttonPrevious).setBackgroundResource(R.drawable.my_border);
                view.findViewById(R.id.buttonUpcomming).setBackgroundResource(R.color.transparentcolor);
                new GamesSourceDowloader(buttonCheck, leagueIdCheck, cardAdapter).execute();
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
            }
        });

        bundle = getArguments();
        ArrayList<Leagues> specLeagues = (ArrayList<Leagues>) bundle.getSerializable("SPECIFIC_LEAGUE_LIST");
        Log.d("specLeagues", specLeagues.stream().map(Leagues::getLeagueName).collect(Collectors.toList()).toString());
        leaguesString = (ArrayList<String>) specLeagues.stream().map(Leagues::getLeagueName).collect(Collectors.toList());
        arrayAdapter = (ArrayAdapter<String>) new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, leaguesString);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String leagueName = parent.getItemAtPosition(position).toString();
                specLeague = specLeagues.stream().filter(obj -> obj.getLeagueName().equals(leagueName)).findFirst().orElse(null);
                leagueIdCheck = specLeague.getLeagueId();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        gamesSourceDowloader = new GamesSourceDowloader(buttonCheck, leagueIdCheck, cardAdapter);
                        gamesSourceDowloader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    }
                }, 500);

                teamsSourceDownloader = new TeamsSourceDownloader(cardAdapter, specLeague.getLeagueName());
                teamsSourceDownloader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                //new GamesSourceDowloader(buttonCheck, leagueIdCheck, cardAdapter).execute();
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
            mListener.onGamesFragmentInteraction();
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
        void onGamesFragmentInteraction();
    }

    public void setGamesSources(ArrayList<Games> sourceOfGamesListUpcoming, ArrayList<Games> sourceOfGamesListPrevious) {
        gamesListUpcoming.addAll(sourceOfGamesListUpcoming);
        gamesListPrevious.addAll(sourceOfGamesListPrevious);
        Log.d("GamesSizeUpcoming", "" + gamesListUpcoming.size());
        Log.d("GamesSizeNext", "" + gamesListPrevious.size());
        for (Games name : gamesListUpcoming) {
            Log.d("Game", name.getEventName());
        }
        for (Games name : gamesListPrevious) {
            Log.d("Game", name.getEventName());
        }
    }
}
