package com.uncc.footballsportsapp.utils;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.uncc.footballsportsapp.model.Leagues;
import com.uncc.footballsportsapp.MainActivity;
import com.uncc.footballsportsapp.model.Sports;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class SportsSourceDownloader extends AsyncTask<String, Integer, String> {
    private static final String TAG = "SportsSourceDownloader";
    private String sportsQuery  = "https://www.thesportsdb.com/api/v1/json/1/all_sports.php";
    private String leaguesQuery = "https://www.thesportsdb.com/api/v1/json/1/all_leagues.php";
    private Uri.Builder buildURL1 = null;
    private Uri.Builder buildURL2 = null;
    private StringBuilder stringBuilder;
    private boolean blank =false;
    boolean isBlank =true;
    private ArrayList<Sports> sourceOfSportsArrayList = new ArrayList <Sports>();
    private ArrayList<Leagues> sourceOfLeaguesArrayList = new ArrayList<Leagues>();
    MainActivity mainActivity;

    public SportsSourceDownloader(MainActivity ma){
        mainActivity = ma;
    }

    @Override
    protected String doInBackground(String... strings) {
        String query1 ="";
        query1 = sportsQuery;
        String query2 = "";
        query2 = leaguesQuery;
        buildURL1 = Uri.parse(query1).buildUpon();
        connectToAPI(buildURL1);
        if(!isBlank) {
            parseJSON1(stringBuilder.toString());
        }
        buildURL2 = Uri.parse(query2).buildUpon();
        connectToAPI(buildURL2);
        if(!isBlank){
            parseJSON2(stringBuilder.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mainActivity.setSources(sourceOfSportsArrayList,sourceOfLeaguesArrayList);
        mainActivity.showFragment();
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
                Toast.makeText(mainActivity.getApplicationContext(),"Check network connection or API not working ",Toast.LENGTH_SHORT).show();
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
                JSONArray sportsArray = jObjMain.getJSONArray("sports");
                for(int i=0;i<sportsArray.length();i++){
                    JSONObject article = (JSONObject) sportsArray.get(i);
                    Sports sportsObj = new Sports();
                    sportsObj.setSportName(article.getString("strSport"));
                    sportsObj.setSportThumbnail(article.getString("strSportThumb"));
                    sourceOfSportsArrayList.add(sportsObj);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void parseJSON2(String s) {
        try{
            if(!blank){
                JSONObject jObjMain = new JSONObject(s);
                JSONArray leaguesArray = jObjMain.getJSONArray("leagues");
                for(int i=0;i<leaguesArray.length();i++){
                    JSONObject article = (JSONObject) leaguesArray.get(i);
                    Leagues leaguessObj = new Leagues();
                    leaguessObj.setLeagueId(article.getString("idLeague"));
                    leaguessObj.setLeagueName(article.getString("strLeague"));
                    leaguessObj.setSportName(article.getString("strSport"));
                    sourceOfLeaguesArrayList.add(leaguessObj);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


}
