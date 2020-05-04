package com.uncc.footballsportsapp.model;

import java.io.Serializable;

public class Sports implements Serializable {

    private String SportName;
    private String SportThumbnail;

    public String getSportName() {
        return SportName;
    }

    public void setSportName(String sportName) {
        SportName = sportName;
    }

    public String getSportThumbnail() {
        return SportThumbnail;
    }

    public void setSportThumbnail(String sportThumbnail) {
        SportThumbnail = sportThumbnail;
    }
}
