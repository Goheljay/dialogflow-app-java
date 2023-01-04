package com.example.musicsystem.repositary;

import com.example.musicsystem.MusicApplication;
import com.example.musicsystem.entity.ApiResponse;
import com.example.musicsystem.entity.ArtistEntity;
import com.google.actions.api.ActionRequest;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ResourceBundle;


public class MusicRepo {
    public static final Logger logger = LoggerFactory.getLogger(MusicApplication.class);

    public ArtistEntity artistName(ActionRequest req )  {
        ArtistEntity entity = new ArtistEntity();
        String name = (String) req.getParameter("artistName");
        logger.info("artist name : {}", name);
        ResourceBundle rb = ResourceBundle.getBundle("url");
        String url = rb.getString("uri")+"/artist/find/" + req.getParameter("artistName");
        OkHttpClient client = new OkHttpClient();
        try {
            Request apiRequest = new Request.Builder().url(url).get().addHeader("content-type", "application/json").addHeader("cache-control", "no-cache").addHeader("postman-token", "698729e5-eca3-226c-4d97-89f36089c9de").build();
            Response response = client.newCall(apiRequest).execute();
            JSONObject json = new JSONObject(response.body().string());
            ApiResponse apiResponse1 = new ApiResponse();
            apiResponse1.setData(json.get("data"));
            entity = new Gson().fromJson (apiResponse1.getData().toString(), ArtistEntity.class);

            System.out.println("artist = " + new Gson().toJson(entity));

            System.out.println("artist name = " + entity.getArtistName());
            logger.info("API CALL EXECUTED");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return entity;
    }
}
