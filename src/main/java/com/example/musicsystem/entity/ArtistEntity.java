package com.example.musicsystem.entity;

public class ArtistEntity {
    private int id;
    private String artistName;
    private String songList;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getSongList() {
        return songList;
    }

    public void setSongList(String songList) {
        this.songList = songList;
    }

    public ArtistEntity(String artistName, String songList) {
        this.artistName = artistName;
        this.songList = songList;
    }

    public ArtistEntity() {
    }
}
