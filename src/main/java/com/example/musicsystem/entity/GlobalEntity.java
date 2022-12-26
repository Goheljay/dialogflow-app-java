package com.example.musicsystem.entity;


public class GlobalEntity {

    private String userName;
    private Boolean userFlag = false;
    private Boolean artistNameFlag = false;
    private Boolean playTypeFlag = false;

    public Boolean getPlayTypeFlag() {
        return playTypeFlag;
    }

    public void setPlayTypeFlag(Boolean playTypeFlag) {
        this.playTypeFlag = playTypeFlag;
    }

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

    public GlobalEntity(String userName, Boolean userFlag, Boolean artistNameFlag, Boolean playTypeFlag) {
        this.userName = userName;
        this.userFlag = userFlag;
        this.artistNameFlag = artistNameFlag;
        this.playTypeFlag = playTypeFlag;
    }

    public GlobalEntity() {
    }
}
