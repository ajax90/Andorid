package com.uncc.footballsportsapp.utils;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.uncc.footballsportsapp.adapters.CardAdapter;
import com.uncc.footballsportsapp.model.Games;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GamesSourceDowloader extends AsyncTask<String, Integer, ArrayList<Games>> {
    private static final String TAG = "GamesSourceDownloader";
    private String gamesUpcQuery  = "https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id=";
    private String gamesPreQuery = "https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?id=";
    private Uri.Builder buildURL = null;
    private Uri.Builder buildURL2 = null;
    private StringBuilder stringBuilder;
    private boolean blank =false;
    boolean isBlank =true;
    private ArrayList<Games> sourceOfGamesArrayList = new ArrayList <Games>();
    String buttonCheck;
    CardAdapter mAdapter;
    String leagueId;

    public GamesSourceDowloader(String buttonCheck, String leagueId, CardAdapter mAdapter){
        this.buttonCheck = buttonCheck;
        this.leagueId = leagueId;
        this.mAdapter = mAdapter;
    }

    @Override
    protected ArrayList<Games> doInBackground(String... strings) {
        String query ="";
        if(buttonCheck.equals("upc")) {
            query = gamesUpcQuery + leagueId;
        }
        else if(buttonCheck.equals("pre")) {
            query = gamesPreQuery + leagueId;
        }
        buildURL = Uri.parse(query).buildUpon();
        connectToAPI(buildURL);
        if(!isBlank) {
            parseJSON1(stringBuilder.toString());
        }
        return sourceOfGamesArrayList;
    }

    @Override
    protected void onPostExecute(ArrayList<Games> s) {
        super.onPostExecute(s);
        //gamesActivity.setGamesSources(sourceOfUpcGamesArrayList,sourceOfPreGamesArrayList);
        Log.d("Upc Games now", sourceOfGamesArrayList.toString());
        mAdapter.setNewList(sourceOfGamesArrayList);
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
                JSONArray gamesArray = jObjMain.getJSONArray("events");
                for(int i=0;i<gamesArray.length();i++){
                    JSONObject article = (JSONObject) gamesArray.get(i);
                    Games gamesObj = new Games();
                    gamesObj.setEventName(article.getString("strEvent"));
                    gamesObj.setEventFileName(article.getString("strFilename"));
                    gamesObj.setLeagueName(article.getString("strLeague"));
                    gamesObj.setSeason(article.getString("strSeason"));
                    gamesObj.setHomeTeam(article.getString("strHomeTeam"));
                    gamesObj.setAwayTeam(article.getString("strAwayTeam"));
                    gamesObj.setHomeScore(article.getString("intHomeScore"));
                    gamesObj.setAwayScore(article.getString("intAwayScore"));
                    gamesObj.setHomeRedCard(article.getString("strHomeRedCards"));
                    gamesObj.setHomeYellowCards(article.getString("strHomeYellowCards"));
                    gamesObj.setAwayRedCard(article.getString("strAwayRedCards"));
                    gamesObj.setAwayYellowCards(article.getString("strAwayYellowCards"));
                    gamesObj.setHomeShots(article.getString("intHomeShots"));
                    gamesObj.setAwayShots(article.getString("intAwayShots"));
                    gamesObj.setEventDate(article.getString("strDate"));
                    gamesObj.setLocalTime(article.getString("strTimeLocal"));
                    gamesObj.setHomeTeamID(article.getString("idHomeTeam"));
                    gamesObj.setAwayTeamID(article.getString("idAwayTeam"));
                    gamesObj.setPostpone(article.getString("strPostponed"));
                    gamesObj.setHomeDefense(article.getString("strHomeLineupDefense"));
                    gamesObj.setAwayDefense(article.getString("strAwayLineupDefense"));
                    gamesObj.setHomeForward(article.getString("strHomeLineupForward"));
                    gamesObj.setAwayForward(article.getString("strAwayLineupForward"));
                    gamesObj.setHomeGoalKeeper(article.getString("strHomeLineupGoalkeeper"));
                    gamesObj.setAwayGoalKeeper(article.getString("strAwayLineupGoalkeeper"));
                    gamesObj.setHomeMidfield(article.getString("strHomeLineupMidfield"));
                    gamesObj.setAwayMidfield(article.getString("strAwayLineupMidfield"));
                    gamesObj.setHomeSubstitute(article.getString("strHomeLineupSubstitutes"));
                    gamesObj.setAwaySubstitute(article.getString("strAwayLineupSubstitutes"));
                    sourceOfGamesArrayList.add(gamesObj);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
