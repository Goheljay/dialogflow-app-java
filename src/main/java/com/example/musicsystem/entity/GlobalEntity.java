package com.example.musicsystem.entity;


public class GlobalEntity {

    private String userName;
    private Boolean userFlag = false;
    private Boolean artistNameFlag = false;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getUserFlag() {
        return userFlag;
    }

    public void setUserFlag(Boolean userFlag) {
        this.userFlag = userFlag;
    }

    public Boolean getArtistNameFlag() {
        return artistNameFlag;
    }

    public void setArtistNameFlag(Boolean artistNameFlag) {
        this.artistNameFlag = artistNameFlag;
    }

    public GlobalEntity(String userName, Boolean userFlag, Boolean artistNameFlag) {
        this.userName = userName;
        this.userFlag = userFlag;
        this.artistNameFlag = artistNameFlag;
    }

    public GlobalEntity() {
    }
}
