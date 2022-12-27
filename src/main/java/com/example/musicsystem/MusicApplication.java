package com.example.musicsystem;

import com.example.musicsystem.entity.GlobalEntity;
import com.example.musicsystem.service.MusicService;
import com.example.musicsystem.serviceImpl.MusicServiceimpl;
import com.google.actions.api.*;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the main class of the Music System sample.
 * It extends the {@link DialogflowApp} class which is the base class for
 * a Dialogflow app.
 */

public class MusicApplication extends DialogflowApp {
    GlobalEntity globalEntity = new GlobalEntity();
    private static final Logger LOGGER = LoggerFactory.getLogger(MusicApplication.class);

    private static final MusicService musicService = new MusicServiceimpl();
    @ForIntent("Default Welcome Intent")
    public ActionResponse welcome(ActionRequest req) {
        LOGGER.info("welcome intent start");
        LOGGER.info("Request : {}", new Gson().toJson(req));
        ActionResponse resp = musicService.welcome(req);
        LOGGER.info("Response : {}", new Gson().toJson(resp));
        return resp;
    }

    @ForIntent("Ask name")
    public ActionResponse askName(ActionRequest req) {
        LOGGER.info("welcome name intent start");
        LOGGER.info("Request : {}", new Gson().toJson(req));
        ActionResponse resp = musicService.askName(req);
        LOGGER.info("Response : {}", new Gson().toJson(resp));
        return resp;
    }

    @ForIntent("play song way")
    public ActionResponse typeToPlay(ActionRequest req) {
        LOGGER.info("type to play intent start");
        LOGGER.info("Request : {}", new Gson().toJson(req));
        globalEntity.setPaginationOneFlag(true);
        ActionResponse resp = musicService.typeToPlay(req, globalEntity);
        LOGGER.info("Response : {}", new Gson().toJson(resp));
        return resp;
    }
    @ForIntent("play song second")
    public ActionResponse playSecondSong(ActionRequest req) {
        LOGGER.info("play second song intent start");
        LOGGER.info("Request : {}", new Gson().toJson(req));
        if (!globalEntity.getPaginationTwoFlag()){
            globalEntity.setPaginationTwoFlag(true);
        } else  {
            globalEntity.setPaginationThreeFlag(true);
        }
        ActionResponse resp = musicService.typeToPlay(req,globalEntity);
        LOGGER.info("Response : {}", new Gson().toJson(resp));
        return resp;
    }

    @ForIntent("Play Song")
    public  ActionResponse playSong(ActionRequest req) {
        LOGGER.info("welcome Song Start");
        LOGGER.info("Request : {}", new Gson().toJson(req));
        ActionResponse resp = musicService.playSong(req);
        LOGGER.info("Response : {}", new Gson().toJson(resp));
        return resp;
    }

    @ForIntent("Media Status")
    public ActionResponse mediaStatus(ActionRequest req) {
        LOGGER.info("Play Status");
        LOGGER.info("Request : {}", new Gson().toJson(req));
        ActionResponse resp = musicService.mediaStatus(req);
        return resp;
    }

    @ForIntent("Media Status Next")
    public ActionResponse mediaStatusNext(ActionRequest req) {
        LOGGER.info("Media Status Next");
        LOGGER.info("Request : {}", new Gson().toJson(req));
        ActionResponse resp = musicService.mediaStatusNext(req);
        return resp;
    }

    @ForIntent("Play song resp yes")
    public ActionResponse yes(ActionRequest req) {
        LOGGER.info("welcome Yes Play Song start");
        LOGGER.info("Request : {}", new Gson().toJson(req));
        ActionResponse resp = musicService.yesResponse(req);
        LOGGER.info("Response : {}", new Gson().toJson(resp));
        return resp;
    }

    @ForIntent("Play song resp no")
    public ActionResponse no(ActionRequest req) {
        LOGGER.info("welcome Yes Play Song start");
        LOGGER.info("Request : {}", new Gson().toJson(req));
        ActionResponse resp = musicService.noResponse(req);
        LOGGER.info("Response : {}", new Gson().toJson(resp));
        return resp;
    }


    @ForIntent("Default Fallback Intent")
    public ActionResponse fallbackIntentHandler(ActionRequest req) {
        LOGGER.info("fallback intent handler start");
        ActionResponse resp = musicService.fallBack(req);
        LOGGER.info("Response : {}", new Gson().toJson(resp));
        LOGGER.info("fallback intent handler end");
        return resp;
    }

}