package com.example.musicsystem.entity;

public class FavoriteEntity {

    private Integer id;

    private String conversionId;

    private String name;

    private String songName;

    public FavoriteEntity(Integer id, String conversionId, String name, String songName) {
        this.id = id;
        this.conversionId = conversionId;
        this.name = name;
        this.songName = songName;
    }

    public FavoriteEntity(String conversionId, String name, String songName) {
        this.conversionId = conversionId;
        this.name = name;
        this.songName = songName;
    }

    public FavoriteEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConversionId() {
        return conversionId;
    }

    public void setConversionId(String conversionId) {
        this.conversionId = conversionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }
}
