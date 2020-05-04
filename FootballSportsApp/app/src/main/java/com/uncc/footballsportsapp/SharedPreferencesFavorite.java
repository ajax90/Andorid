package com.uncc.footballsportsapp;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.uncc.footballsportsapp.model.Teams;

public class SharedPreferencesFavorite {

    public static final String APPS_NAME = "SPORTS_APP";
    public static final String FAVORITES = "TEAM_Favorite";

    public SharedPreferencesFavorite() {
        super();
    }

    // This four methods are used for maintaining favorites.
    public void saveFavorites(Context context, List<Teams> favorites) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(APPS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);
        editor.putString(FAVORITES, jsonFavorites);
        editor.commit();
    }

    public void addFavorite(Context context, Teams product) {
        List<Teams> favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<Teams>();
        favorites.add(product);
        saveFavorites(context, favorites);
    }

    public void removeFavorite(Context context, Teams team) {
        ArrayList<Teams> favorites = getFavorites(context);
        if (favorites != null) {
            for (Teams teams : favorites) {
                if (teams.getTeamName().equals(team.getTeamName())) {
                    favorites.remove(teams);
                    break;
                }
            }
            saveFavorites(context, favorites);
        }
    }

    public ArrayList<Teams> getFavorites(Context context) {
        SharedPreferences settings;
        List<Teams> favorites;
        settings = context.getSharedPreferences(APPS_NAME, Context.MODE_PRIVATE);
        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            Teams[] favoriteItems = gson.fromJson(jsonFavorites, Teams[].class);
            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<Teams>(favorites);
        } else
            return null;

        return (ArrayList<Teams>) favorites;
    }
}