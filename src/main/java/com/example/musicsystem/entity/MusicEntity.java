package com.example.musicsystem.entity;

public class MusicEntity {
    private int id;
    private String audio_link;
    private String song_name;
    private String album_art_link;
    private String artist_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAudio_link() {
        return audio_link;
    }

    public void setAudio_link(String audio_link) {
        this.audio_link = audio_link;
    }

    public String getSong_name() {
        return song_name;
    }

    public void setSong_name(String song_name) {
        this.song_name = song_name;
    }

    public String getAlbum_art_link() {
        return album_art_link;
    }

    public void setAlbum_art_link(String album_art_link) {
        this.album_art_link = album_art_link;
    }

    public String getArtist_name() {
        return artist_name;
    }

    public void setArtist_name(String artist_name) {
        this.artist_name = artist_name;
    }

    public MusicEntity(int id, String audio_link, String song_name, String album_art_link, String artist_name) {
        this.id = id;
        this.audio_link = audio_link;
        this.song_name = song_name;
        this.album_art_link = album_art_link;
        this.artist_name = artist_name;
    }
}
