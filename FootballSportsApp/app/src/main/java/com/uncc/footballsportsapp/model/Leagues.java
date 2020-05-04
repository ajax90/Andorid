package com.uncc.footballsportsapp.model;

import java.io.Serializable;

public class Leagues implements Serializable {

    private String LeagueId;
    private String LeagueName;
    private String SportName;

    public String getSportName() {
        return SportName;
    }

    public void setSportName(String sportName) {
        SportName = sportName;
    }

    public String getLeagueName() {
        return LeagueName;
    }

    public void setLeagueName(String leagueName) {
        LeagueName = leagueName;
    }

    public String getLeagueId() {
        return LeagueId;
    }

    public void setLeagueId(String leagueId) {
        LeagueId = leagueId;
    }
}
