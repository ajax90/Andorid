package com.uncc.footballsportsapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.io.Serializable;
import java.util.ArrayList;

import com.uncc.footballsportsapp.fragments.FavoritesFragment;
import com.uncc.footballsportsapp.fragments.GamesFragment;
import com.uncc.footballsportsapp.fragments.ScoreBoardFragment;
import com.uncc.footballsportsapp.fragments.SportsFragment;
import com.uncc.footballsportsapp.fragments.TeamInfoFragment;
import com.uncc.footballsportsapp.fragments.TeamsFragment;
import com.uncc.footballsportsapp.model.Leagues;
import com.uncc.footballsportsapp.model.Sports;
import com.uncc.footballsportsapp.model.Teams;
import com.uncc.footballsportsapp.utils.SportsSourceDownloader;

public class MainActivity extends AppCompatActivity implements SportsFragment.OnFragmentInteractionListener,
        GamesFragment.OnFragmentInteractionListener, FavoritesFragment.OnFragmentInteractionListener,
        TeamsFragment.OnFragmentInteractionListener, TeamInfoFragment.OnFragmentInteractionListener,
        ScoreBoardFragment.OnFragmentInteractionListener {

    ArrayList<Sports> sportsList = new ArrayList<>();
    ArrayList<Leagues> leaguesList = new ArrayList<>();
    ArrayList<Teams> teamsByLeaguesList = new ArrayList<>();
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //MainActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (isOnline()) {
            new SportsSourceDownloader(this).execute();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("No internet Connection");
            builder.setMessage("Please turn on internet connection to continue");
            builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                }
            });
            builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            new SportsSourceDownloader(this).execute();
        }
    }

    public void showFragment() {
        SportsFragment fragment = new SportsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("SPORTS_KEY", (Serializable) sportsList);
        bundle.putSerializable("LEAGUES_KEY", (Serializable) leaguesList);
        fragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.layoutContainer, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 1) {
            getFragmentManager().popBackStack();
        } else
            super.onBackPressed();
    }


    @Override
    public void onFragmentInteraction() {
        //Toast.makeText(this, "Sports Fragment Loaded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGamesFragmentInteraction() {
        //Toast.makeText(this, "Games Fragment Loaded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFavoriteFragmentInteraction() {
        //Toast.makeText(this, "Favorite Fragment Loaded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTeamsFragmentInteraction() {
        //Toast.makeText(this, "Teams Fragment Loaded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTeamInfoFragmentInteraction() {
        //Toast.makeText(this, "Teams Info Fragment Loaded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onScoreBoardFragmentInteraction() {
        //Toast.makeText(this, "Score Board Info Fragment Loaded", Toast.LENGTH_SHORT).show();
    }

    public void setSources(ArrayList<Sports> sourceOfSportsList, ArrayList<Leagues> sourceOfLeaguesList) {
        sportsList.addAll(sourceOfSportsList);
        leaguesList.addAll(sourceOfLeaguesList);
        Log.d("LeaguesSize", "" + leaguesList.size());
        for (Leagues name : leaguesList) {
            Log.d("Leagues", name.getLeagueName());
        }
        //ArrayList<Leagues> specificList = (ArrayList<Leagues>) leaguesList.stream().filter(obj -> obj.getSportName().equals("Soccer")).collect(Collectors.toList());

        //Log.d("SpecificLeagues", ""+specificList.size());

    }

    public void setTeamsSources(ArrayList<Teams> sourceOfTeamsList) {
        teamsByLeaguesList.addAll(sourceOfTeamsList);
        Log.d("TeamsSize", "" + sourceOfTeamsList.size());
        for (Teams name : sourceOfTeamsList) {
            Log.d("Team", name.getTeamName());
        }
    }

    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
            //Toast.makeText(getApplicationContext(), "No Internet connection!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}
