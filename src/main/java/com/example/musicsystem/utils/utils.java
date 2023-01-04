package com.example.musicsystem.utils;

import com.example.musicsystem.MusicApplication;
import com.example.musicsystem.entity.ArtistEntity;
import com.example.musicsystem.entity.MusicEntity;
import com.google.actions.api.ActionRequest;
import com.google.actions.api.response.ResponseBuilder;
import com.google.api.services.actions_fulfillment.v2.model.Image;
import com.google.api.services.actions_fulfillment.v2.model.MediaObject;
import com.google.api.services.actions_fulfillment.v2.model.MediaResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class utils {
    private static final Logger LOGGER = LoggerFactory.getLogger(MusicApplication.class);
    OkHttpClient client = new OkHttpClient();

    String imgUrl = "https://storage.googleapis.com/automotive-media/album_art.jpg";
    public int randomNumberGenerator(int min, int max) {
        int num = (int) (Math.random() * (1 - (max == 0 ? min : max)));
        if (num == 0 || num < 0) {
            num = min;
        }
        return num;
    }
    public ResponseBuilder playMediaGenere(ActionRequest req,  List<MusicEntity> entities) {
        int num = randomNumberGenerator(1, entities.size());
        ResponseBuilder resp = new ResponseBuilder();
        entities.forEach(entity -> {
            if (entity.getId() == num) {
                LOGGER.info("song genere : {}", entity);
                resp.add("Playing " + entity.getArtist_name() + " Song");
                resp.addSuggestions(new String[]{"Okay", "Cancel", "Next"});
                resp.add(
                        new MediaResponse()
                                .setMediaObjects(
                                        Collections.singletonList(
                                                new MediaObject()
                                                        .setName(entity.getSong_name())
                                                        .setDescription(entity.getArtist_name())
                                                        .setContentUrl(entity.getAudio_link())
                                                        .setIcon(new Image()
                                                                .setUrl(imgUrl)
                                                                .setAccessibilityText("Album cover of an ocean view"))))
                                .setMediaType("AUDIO"));
            }
        });
        return resp;
    }

    public  ResponseBuilder playMedia(ActionRequest req, ArtistEntity entities) {
        ResponseBuilder resp = new ResponseBuilder();
        resp.add("Playing " + req.getParameter("artistName") + " song");
        resp.addSuggestions(new String[]{"Okay", "Cancel", "Next"});
        resp.add(
                new MediaResponse()
                        .setMediaObjects(
                                Collections.singletonList(
                                        new MediaObject()
                                                .setName(entities.getArtistName())
                                                .setDescription(entities.getArtistName())
                                                .setContentUrl(entities.getSongList())
                                                .setIcon(
                                                        new Image()
                                                                .setUrl(imgUrl)
                                                                .setAccessibilityText(
                                                                        "Album cover of an ocean view"))))
                        .setMediaType("AUDIO"));

        return resp;
    }

    public String convertSSMLSpeech (String speech){
        String newSpeech = speech.replace("@", "<break time='0.5s' strength='weak'/>");
        return "<speak>" + newSpeech + "</speak>";
    }

    public String convertSSMLSpeech (String speech, double breakTime, String name){
        String speechContent = speech.replace("#", name);
        String newSpeech = speechContent.replace("@", "<break time='" + breakTime + "s' strength='weak'/>");
        return "<speak>" + newSpeech + "</speak>";
    }
}
