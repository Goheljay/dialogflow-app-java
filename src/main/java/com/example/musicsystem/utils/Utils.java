package com.example.musicsystem.utils;

import com.example.musicsystem.MusicApplication;
import com.example.musicsystem.requestDto.SessionDto;
import com.example.musicsystem.responseDto.ApiResponse;
import com.example.musicsystem.entity.ArtistEntity;
import com.example.musicsystem.entity.MusicEntity;
import com.google.actions.api.ActionRequest;
import com.google.actions.api.response.ResponseBuilder;
import com.google.api.services.actions_fulfillment.v2.model.Image;
import com.google.api.services.actions_fulfillment.v2.model.MediaObject;
import com.google.api.services.actions_fulfillment.v2.model.MediaResponse;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class Utils {
    private static final Logger LOGGER = LoggerFactory.getLogger(MusicApplication.class);
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
                resp.add("Playing " + entity.getArtistName() + " Song");
                resp.addSuggestions(new String[]{"Okay", "Cancel", "Next"});
                resp.add(
                        new MediaResponse()
                                .setMediaObjects(
                                        Collections.singletonList(
                                                new MediaObject()
                                                        .setName(entity.getSongName())
                                                        .setDescription(entity.getArtistName())
                                                        .setContentUrl(entity.getAudioLink())
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

    public  ResponseBuilder playMediaMusic(ActionRequest req, MusicEntity entities) {
        ResponseBuilder resp = new ResponseBuilder();
        resp.add("Playing " + entities.getArtistName() + " song");
        resp.addSuggestions(new String[]{"Okay", "Cancel", "Next"});
        resp.add(
                new MediaResponse()
                        .setMediaObjects(
                                Collections.singletonList(
                                        new MediaObject()
                                                .setName(entities.getArtistName())
                                                .setDescription(entities.getArtistName())
                                                .setContentUrl(entities.getAudioLink())
                                                .setIcon(
                                                        new Image()
                                                                .setUrl(entities.getAlbumArtLink())
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

    public ApiResponse apiResp(String url) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            OkHttpClient client = new OkHttpClient();
            Request apiRequest = new Request.Builder().url(url).get().addHeader("content-type", "application/json").addHeader("cache-control", "no-cache").addHeader("postman-token", "698729e5-eca3-226c-4d97-89f36089c9de").build();
            Response response = client.newCall(apiRequest).execute();
            JSONObject json = new JSONObject(response.body().string());
            LOGGER.info("json : {}", json);

            apiResponse.setData(json.get("data"));
            apiResponse.setMassage(json.get("message").toString());
            apiResponse.setCode(json.get("status").toString());

            LOGGER.info("apiResponse : {}", apiResponse);
            LOGGER.info("API CALL EXECUTED");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return apiResponse;
    }

    public ApiResponse sessionApi (String uri, RequestBody body) {
        ApiResponse apiResp = new ApiResponse();
        try {
            OkHttpClient client = new OkHttpClient();
            Request apiRequest = new Request.Builder().url(uri).post(body).addHeader("content-type", "application/json").addHeader("cache-control", "no-cache").build();
            Response response = client.newCall(apiRequest).execute();
            String data = response.body().string();
            LOGGER.info("data: " + data);
            JSONObject getJson = new JSONObject(data);
            LOGGER.info("json: " + new Gson().toJson(getJson));

            apiResp.setData(getJson.get("data"));
            apiResp.setMassage(getJson.get("message").toString());
            apiResp.setCode(getJson.get("status").toString());

            LOGGER.info("apiResp: " + apiResp);
            LOGGER.info("apiResp Data: " + apiResp.getData());
            LOGGER.info("API CALL EXECUTED");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return apiResp;
    }
}
