package com.example.musicsystem.entity;


public class GlobalEntity {

    private String userName;

    private  String conversionId;

    private  String artistName;
    private Boolean userFlag = false;
    private Boolean artistNameFlag = false;
    private Boolean playGenereFlag = false;
    private  Boolean selectedWay = false;
    private Boolean optionFlag = false;
    private Boolean paginationOneFlag = false;
    private Boolean paginationTwoFlag = false;
    private Boolean paginationThreeFlag = false;
    private Boolean repeatFlag = false;

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    private Boolean fallbackTypingFlag = false;

    public String getConversionId() {
        return conversionId;
    }

    public void setConversionId(String conversionId) {
        this.conversionId = conversionId;
    }

    public Boolean getFallbackTypingFlag() {
        return fallbackTypingFlag;
    }

    public void setFallbackTypingFlag(Boolean fallbackTypingFlag) {
        this.fallbackTypingFlag = fallbackTypingFlag;
    }

    public Boolean getRepeatFlag() {
        return repeatFlag;
    }

    public void setRepeatFlag(Boolean repeatFlag) {
        this.repeatFlag = repeatFlag;
    }

    public Boolean getPaginationOneFlag() {
        return paginationOneFlag;
    }

    public void setPaginationOneFlag(Boolean paginationOneFlag) {
        this.paginationOneFlag = paginationOneFlag;
    }

    public Boolean getPaginationTwoFlag() {
        return paginationTwoFlag;
    }

    public void setPaginationTwoFlag(Boolean paginationTwoFlag) {
        this.paginationTwoFlag = paginationTwoFlag;
    }

    public Boolean getPaginationThreeFlag() {
        return paginationThreeFlag;
    }

    public void setPaginationThreeFlag(Boolean paginationThreeFlag) {
        this.paginationThreeFlag = paginationThreeFlag;
    }

    public Boolean getOptionFlag() {
        return optionFlag;
    }

    public void setOptionFlag(Boolean optionFlag) {
        this.optionFlag = optionFlag;
    }

    public Boolean getSelectedWay() {
        return selectedWay;
    }

    public void setSelectedWay(Boolean selectedWay) {
        this.selectedWay = selectedWay;
    }

    public Boolean getPlayGenereFlag() {
        return playGenereFlag;
    }

    public void setPlayGenereFlag(Boolean playGenereFlag) {
        this.playGenereFlag = playGenereFlag;
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

    public GlobalEntity(String userName, String conversionId, Boolean userFlag, Boolean artistNameFlag, Boolean playGenereFlag, Boolean selectedWay, Boolean optionFlag, Boolean paginationOneFlag, Boolean paginationTwoFlag, Boolean paginationThreeFlag, Boolean repeatFlag, Boolean fallbackTypingFlag) {
        this.userName = userName;
        this.conversionId = conversionId;
        this.userFlag = userFlag;
        this.artistNameFlag = artistNameFlag;
        this.playGenereFlag = playGenereFlag;
        this.selectedWay = selectedWay;
        this.optionFlag = optionFlag;
        this.paginationOneFlag = paginationOneFlag;
        this.paginationTwoFlag = paginationTwoFlag;
        this.paginationThreeFlag = paginationThreeFlag;
        this.repeatFlag = repeatFlag;
        this.fallbackTypingFlag = fallbackTypingFlag;
    }

    @Override
    public String toString() {
        return "GlobalEntity{" +
                "userName='" + userName + '\'' +
                ", conversionId='" + conversionId + '\'' +
                ", userFlag=" + userFlag +
                ", artistNameFlag=" + artistNameFlag +
                ", playGenereFlag=" + playGenereFlag +
                ", selectedWay=" + selectedWay +
                ", optionFlag=" + optionFlag +
                ", paginationOneFlag=" + paginationOneFlag +
                ", paginationTwoFlag=" + paginationTwoFlag +
                ", paginationThreeFlag=" + paginationThreeFlag +
                ", repeatFlag=" + repeatFlag +
                ", fallbackTypingFlag=" + fallbackTypingFlag +
                '}';
    }

    public GlobalEntity() {
    }
}
