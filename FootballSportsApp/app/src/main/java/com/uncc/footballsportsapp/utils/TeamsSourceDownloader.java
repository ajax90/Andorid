package com.uncc.footballsportsapp.utils;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.uncc.footballsportsapp.adapters.CardAdapter;
import com.uncc.footballsportsapp.MainActivity;
import com.uncc.footballsportsapp.adapters.TeamAdapter;
import com.uncc.footballsportsapp.model.Teams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class TeamsSourceDownloader extends AsyncTask<String, Integer, ArrayList<Teams>> {
    private static final String TAG = "SportsSourceDownloader";
    private String TeamsByLeaguesQuery  = "https://www.thesportsdb.com/api/v1/json/1/search_all_teams.php?l=";
    private Uri.Builder buildURL1 = null;
    private StringBuilder stringBuilder;
    private boolean blank =false;
    boolean isBlank =true;
    public ArrayList<Teams> sourceOfTeamsArrayList = new ArrayList <Teams>();
    MainActivity mainActivity;
    String leagueName;
    TeamAdapter mAdapter;
    CardAdapter cardAdapter = null;

    public TeamsSourceDownloader(TeamAdapter mAdapter, String leagueName){
        this.mAdapter = mAdapter;
        this.leagueName = leagueName;
    }

    public TeamsSourceDownloader(CardAdapter cardAdapter, String leagueName){
        this.leagueName = leagueName;
        this.cardAdapter = cardAdapter;
    }

    public TeamsSourceDownloader(){

    }

    @Override
    protected ArrayList<Teams> doInBackground(String... strings) {
        String query1 ="";
        query1 = TeamsByLeaguesQuery+leagueName;
        buildURL1 = Uri.parse(query1).buildUpon();
        connectToAPI(buildURL1);
        if(!isBlank) {
            parseJSON1(stringBuilder.toString());
        }
        /*scoreBoardFragment = new ScoreBoardFragment();
        Log.d("teamListTeamourceDownloader", ""+sourceOfTeamsArrayList.size());
        scoreBoardFragment.setTeamsList(sourceOfTeamsArrayList);*/
        return sourceOfTeamsArrayList;
    }

    @Override
    protected void onPostExecute(ArrayList<Teams> s) {
        super.onPostExecute(s);
        if(cardAdapter == null) {
            mAdapter.setNewTeamList(sourceOfTeamsArrayList);
        }
        else{
            cardAdapter.setTeamsList(sourceOfTeamsArrayList);
        }

    }

    public void connectToAPI(Uri.Builder buildURL) {
        String urlToUse = buildURL.build().toString();
        stringBuilder = new StringBuilder();
        try {
            URL url = new URL(urlToUse);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND)
            {
                blank =true;
            }
            else {
                httpURLConnection.setRequestMethod("GET");
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader reader = new BufferedReader((new InputStreamReader(inputStream)));

                String line=null;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append('\n');
                }
                isBlank =false;

            }
        }
        catch(FileNotFoundException fe){
            Log.d(TAG, "FileNotFoundException ");
        }
        catch (Exception e) {
            //e.printStackTrace();
            Log.d(TAG, "Exception doInBackground: " + e.getMessage());
        }
    }
    private void parseJSON1(String s) {
        try{
            if(!blank){
                JSONObject jObjMain = new JSONObject(s);
                JSONArray teamsArray = jObjMain.getJSONArray("teams");
                for(int i=0;i<teamsArray.length();i++){
                    JSONObject article = (JSONObject) teamsArray.get(i);
                    Teams teamssObj = new Teams();
                    teamssObj.setTeamId(article.getString("idTeam"));
                    teamssObj.setTeamName(article.getString("strTeam"));
                    teamssObj.setShortTeamName(article.getString("strTeamShort"));
                    teamssObj.setFormedYear(article.getInt("intFormedYear"));
                    teamssObj.setStadium(article.getString("strStadium"));
                    teamssObj.setDescription(article.getString("strDescriptionEN"));
                    teamssObj.setBadge(article.getString("strTeamBadge"));
                    teamssObj.setUrl(article.getString("strWebsite"));
                    sourceOfTeamsArrayList.add(teamssObj);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
