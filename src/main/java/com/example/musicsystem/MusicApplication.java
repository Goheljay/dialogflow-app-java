package com.example.musicsystem;

import com.example.musicsystem.entity.GlobalEntity;
import com.example.musicsystem.service.MusicService;
import com.example.musicsystem.serviceImpl.MusicServiceimpl;
import com.google.actions.api.*;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * This is the main class of the Music System sample.
 * It extends the {@link DialogflowApp} class which is the base class for
 * a Dialogflow app.
 */

public class MusicApplication extends DialogflowApp {
    GlobalEntity globalEntity = new GlobalEntity();
    private static final Logger logger = LoggerFactory.getLogger(MusicApplication.class);

    private static final MusicService musicService = new MusicServiceimpl();
    @ForIntent("Default Welcome Intent")
    public ActionResponse welcome(ActionRequest req) {
        logger.info("welcome intent start");
        logger.info("Request : {}", new Gson().toJson(req));
        ActionResponse resp = musicService.welcome(req);
        logger.info("Response : {}", new Gson().toJson(resp));
        return resp;
    }

    @ForIntent("User Name")
    public ActionResponse askName(ActionRequest req) {
        logger.info("welcome name intent start");
        logger.info("Request : {}", new Gson().toJson(req));
        ActionResponse resp = musicService.askName(req, globalEntity);
        logger.info("Response : {}", new Gson().toJson(resp));
        return resp;
    }

    @ForIntent("play song way")
    public ActionResponse typeToPlay(ActionRequest req) {
        logger.info("type to play intent start");
        logger.info("Request : {}", new Gson().toJson(req));
        logger.info("Option flag : {}", req.getParameter("optionFlag"));
        if (Boolean.TRUE.equals(globalEntity.getFallbackTypingFlag())) {
            globalEntity.setOptionFlag(true);
        }
        if (Objects.equals(req.getParameter("optionFlag"), "options")) {
            globalEntity.setOptionFlag(true);
            globalEntity.setPaginationOneFlag(true);
        }
        ActionResponse resp = musicService.typeToPlay(req, globalEntity);
        logger.info("Response : {}", new Gson().toJson(resp));
        return resp;
    }
    @ForIntent("play song second")
    public ActionResponse playSecondSong(ActionRequest req) {
        logger.info("play second song intent start");
        logger.info("Request : {}", new Gson().toJson(req));
        logger.info("Request : {}", req.getParameter("given-name"));
       if (globalEntity.getPaginationOneFlag()){
            globalEntity.setPaginationTwoFlag(true);
            globalEntity.setPaginationOneFlag(false);
            globalEntity.setPaginationThreeFlag(false);
        } else if (globalEntity.getPaginationTwoFlag()){
            globalEntity.setPaginationThreeFlag(true);
            globalEntity.setPaginationOneFlag(false);
            globalEntity.setPaginationTwoFlag(false);
        }
        ActionResponse resp = musicService.typeToPlay(req,globalEntity);
        logger.info("Response : {}", new Gson().toJson(resp));
        return resp;
    }

    @ForIntent("Play Song")
    public  ActionResponse playSong(ActionRequest req) {
        logger.info("welcome Song Start");
        logger.info("Request : {}", new Gson().toJson(req));
        ActionResponse resp = musicService.playSong(req);
        logger.info("Response : {}", new Gson().toJson(resp));
        return resp;
    }

    @ForIntent("Media Status")
    public ActionResponse mediaStatus(ActionRequest req) {
        logger.info("Play Status");
        logger.info("Request : {}", new Gson().toJson(req));
        ActionResponse resp = musicService.mediaStatus(req);
        return resp;
    }

    @ForIntent("Media Status Next")
    public ActionResponse mediaStatusNext(ActionRequest req) {
        logger.info("Media Status Next");
        logger.info("Request : {}", new Gson().toJson(req));
        ActionResponse resp = musicService.mediaStatusNext(req);
        return resp;
    }

    @ForIntent("Play song resp yes")
    public ActionResponse yes(ActionRequest req) {
        logger.info("Play song resp yes");
        logger.info("Request : {}", new Gson().toJson(req));
        globalEntity.setUserFlag(true);
        ActionResponse resp = this.askName(req);
        logger.info("Response : {}", new Gson().toJson(resp));
        return resp;
    }

    @ForIntent("Play song resp no")
    public ActionResponse no(ActionRequest req) {
        logger.info("Play song resp no");
        logger.info("Request : {}", new Gson().toJson(req));
        ActionResponse resp = musicService.noResponse(req);
        logger.info("Response : {}", new Gson().toJson(resp));
        return resp;
    }

    @ForIntent("User Review yes")
    public ActionResponse userReviewYes(ActionRequest req) {
        logger.info("User review yes");
        logger.info("Request : {}", new Gson().toJson(req));
        ActionResponse resp = musicService.yesReviewResponse(req);
        logger.info("Response : {}", new Gson().toJson(resp));
        return resp;
    }
    @ForIntent("User Review no")
    public ActionResponse userReviewNo(ActionRequest req) {
        logger.info("User Review no");
        logger.info("Request : {}", new Gson().toJson(req));
        ActionResponse resp = musicService.noReviewResponse(req);
        logger.info("Response : {}", new Gson().toJson(resp));
        return resp;
    }

    @ForIntent("Default Fallback Intent")
    public ActionResponse fallbackIntentHandler(ActionRequest req) {
        logger.info("Request : {}", new Gson().toJson(req));
        logger.info("fallback intent handler start");
        logger.info("OPtion flag : {}", Objects.equals(req.getParameter("optionFlag"), "options"));
        ActionResponse resp;
        if (Objects.equals(req.getParameter("optionFlag"), "typing")) {
            resp = this.typeToPlay(req);
            globalEntity.setFallbackTypingFlag(true);
        } else {
            resp = musicService.fallBack(req);
        }
        logger.info("Response : {}", new Gson().toJson(resp));
        logger.info("fallback intent handler end");
        return resp;
    }

}