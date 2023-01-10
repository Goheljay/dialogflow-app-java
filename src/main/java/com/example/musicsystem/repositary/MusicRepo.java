package com.example.musicsystem.repositary;

import com.example.musicsystem.MusicApplication;
import com.example.musicsystem.entity.GlobalEntity;
import com.example.musicsystem.requestDto.SessionDto;
import com.example.musicsystem.responseDto.ApiResponse;
import com.example.musicsystem.entity.ArtistEntity;
import com.example.musicsystem.entity.MusicEntity;
import com.example.musicsystem.utils.Utils;
import com.google.actions.api.ActionRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ResourceBundle;


public class MusicRepo {
    public static final Logger logger = LoggerFactory.getLogger(MusicApplication.class);

    public ArtistEntity artistResponse(ActionRequest req)  {
        String name = (String) req.getParameter("artistName");
        logger.info("artist name : {}", name);
        ResourceBundle rb = ResourceBundle.getBundle("url");
        String url = rb.getString("uri")+"/songapp/find/" + req.getParameter("artistName");
        Utils utils = new Utils();
        ApiResponse apiResponse = utils.apiResp(url);
        ArtistEntity entity = new Gson().fromJson (apiResponse.getData().toString(), ArtistEntity.class);
        System.out.println("artist = " + new Gson().toJson(entity));
        System.out.println("artist name = " + entity.getArtistName());
        return entity;
    }

    public MusicEntity musicResponse(ActionRequest req)  {
        String name = (String) req.getParameter("musicName");
        logger.info("music name : {}", name);
        ResourceBundle rb = ResourceBundle.getBundle("url");
        String url = rb.getString("uri")+"/songapp/findMusicByGenere/" + req.getParameter("songGenere");
        Utils utils = new Utils();
        ApiResponse apiResponse = utils.apiResp(url);
        logger.info("apiResponse.getData().toString() :{} ", apiResponse.getData().toString());
        MusicEntity entity = new Gson().fromJson (apiResponse.getData().toString(), MusicEntity.class);
        return entity;
    }

    public SessionDto getSessionResponse (String sessionId)  {
        ResourceBundle rb = ResourceBundle.getBundle("url");
        String url = rb.getString("uri")+"/songapp/getSession/" + sessionId;
        Utils utils = new Utils();
        ApiResponse apiResponse = utils.apiResp(url);
        logger.info("apiResponse.getData().toString() :{} ", apiResponse.getData().toString());
        SessionDto sessionDto = new Gson().fromJson (apiResponse.getData().toString(), SessionDto.class);
        return sessionDto;
    }

    public SessionDto sessionResponse(ActionRequest req,  GlobalEntity globalEntity, Boolean isFallBack)  {

        JsonObject globalEntityJson = new JsonObject();
        globalEntityJson.addProperty("userFlag", globalEntity.getUserFlag());
        globalEntityJson.addProperty("artistNameFlag", globalEntity.getArtistNameFlag());
        globalEntityJson.addProperty("playGenereFlag",globalEntity.getPlayGenereFlag());
        globalEntityJson.addProperty("selectedWay", globalEntity.getSelectedWay());
        globalEntityJson.addProperty("optionFlag", globalEntity.getOptionFlag());
        globalEntityJson.addProperty("paginationOneFlag", globalEntity.getPaginationOneFlag());
        globalEntityJson.addProperty("paginationTwoFlag", globalEntity.getPaginationTwoFlag());
        globalEntityJson.addProperty("paginationThreeFlag", globalEntity.getPaginationThreeFlag());
        globalEntityJson.addProperty("fallbackTypingFlag", globalEntity.getFallbackTypingFlag());
        logger.info("globalEntity : {}", new Gson().toJson(globalEntityJson));

        JSONObject requestParamJson = new JSONObject();
        requestParamJson.put("optionFlag", req.getParameter("optionFlag") == null ? "" : req.getParameter("optionFlag"));
        requestParamJson.put("songGenere",req.getParameter("songGenere") == null ? "" : req.getParameter("songGenere"));
        requestParamJson.put("artistName",req.getParameter("artistName") == null ? "" : req.getParameter("artistName"));
        requestParamJson.put("next", req.getParameter("next") == null ? "" : req.getParameter("next"));

        logger.info("requestParamJson : {}", new Gson().toJson(requestParamJson));

        Utils utils = new Utils();
        ResourceBundle rb = ResourceBundle.getBundle("url");
        String url = rb.getString("uri")+"/songapp/addSession";
        String appReq = req.getAppRequest().toString();
        SessionDto session = new SessionDto();
        session.setIntent(req.getIntent());
        session.setConversionId(globalEntity.getConversionId());
        session.setIntentRequest(appReq);
        session.setRequestParam(new Gson().toJson(requestParamJson));
        session.setUserName(req.getParameter("given-name") == null? "-" : req.getParameter("given-name").toString());
        session.setGlobalEntity(new Gson().toJson(globalEntityJson));
        session.setIsFallBack(isFallBack);

        logger.info("fallback : {}", session.getIsFallBack());
        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"), new Gson().toJson(session));

        ApiResponse apiResponse = utils.sessionApi(url,body);
        logger.info("session Dto : {}", new Gson().toJson(apiResponse.getData()));
        SessionDto sessionDto = new Gson().fromJson(apiResponse.getData().toString(), SessionDto.class);
        return sessionDto;
    }
    public SessionDto updateSessionResponse(ActionRequest req,  GlobalEntity globalEntity, Boolean isFallBack, SessionDto dtoReq)  {

        JsonObject globalEntityJson = new JsonObject();
        globalEntityJson.addProperty("userFlag", globalEntity.getUserFlag());
        globalEntityJson.addProperty("artistNameFlag", globalEntity.getArtistNameFlag());
        globalEntityJson.addProperty("playGenereFlag",globalEntity.getPlayGenereFlag());
        globalEntityJson.addProperty("selectedWay", globalEntity.getSelectedWay());
        globalEntityJson.addProperty("optionFlag", globalEntity.getOptionFlag());
        globalEntityJson.addProperty("paginationOneFlag", globalEntity.getPaginationOneFlag());
        globalEntityJson.addProperty("paginationTwoFlag", globalEntity.getPaginationTwoFlag());
        globalEntityJson.addProperty("paginationThreeFlag", globalEntity.getPaginationThreeFlag());
        globalEntityJson.addProperty("fallbackTypingFlag", globalEntity.getFallbackTypingFlag());
        logger.info("globalEntity : {}", new Gson().toJson(globalEntityJson));

        JSONObject requestParamJson = new JSONObject();
        requestParamJson.put("optionFlag", req.getParameter("optionFlag") == null ? "" : req.getParameter("optionFlag"));
        requestParamJson.put("songGenere",req.getParameter("songGenere") == null ? "" : req.getParameter("songGenere"));
        requestParamJson.put("artistName",req.getParameter("artistName") == null ? "" : req.getParameter("artistName"));
        requestParamJson.put("next", req.getParameter("next") == null ? "" : req.getParameter("next"));

        logger.info("requestParamJson : {}", new Gson().toJson(requestParamJson));

        Utils utils = new Utils();
        ResourceBundle rb = ResourceBundle.getBundle("url");
        String url = rb.getString("uri")+"/songapp/addSession";
        String appReq = req.getAppRequest().toString();
        SessionDto session = new SessionDto();
        session.setId(dtoReq.getId());
        session.setIntent(req.getIntent());
        session.setConversionId(globalEntity.getConversionId());
        session.setIntentRequest(appReq);
        session.setRequestParam(new Gson().toJson(requestParamJson));
        session.setUserName(req.getParameter("given-name") == null? "-" : req.getParameter("given-name").toString());
        session.setGlobalEntity(new Gson().toJson(globalEntityJson));
        session.setIsFallBack(isFallBack);

        logger.info("fallback : {}", session.getIsFallBack());
        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"), new Gson().toJson(session));

        ApiResponse apiResponse = utils.sessionApi(url,body);
        logger.info("session Dto : {}", new Gson().toJson(apiResponse.getData()));
        SessionDto sessionDto = new Gson().fromJson(apiResponse.getData().toString(), SessionDto.class);
        return sessionDto;
    }
}
