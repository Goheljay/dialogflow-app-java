package com.example.musicsystem.entity;

public class MusicEntity {
    private int id;
    private String audioLink;
    private String songName;
    private String albumArtLink;
    private String artistName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAudioLink() {
        return audioLink;
    }

    public void setAudioLink(String audioLink) {
        this.audioLink = audioLink;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getAlbumArtLink() {
        return albumArtLink;
    }

    public void setAlbumArtLink(String albumArtLink) {
        this.albumArtLink = albumArtLink;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public MusicEntity(int id, String audio_link, String song_name, String album_art_link, String artist_name) {
        this.id = id;
        this.audioLink = audio_link;
        this.songName = song_name;
        this.albumArtLink = album_art_link;
        this.artistName = artist_name;
    }

    public MusicEntity() {
    }
}
