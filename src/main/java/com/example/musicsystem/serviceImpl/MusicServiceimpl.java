package com.example.musicsystem.serviceImpl;

import com.example.musicsystem.MusicApplication;
import com.example.musicsystem.entity.ArtistEntity;
import com.example.musicsystem.entity.GlobalEntity;
import com.example.musicsystem.entity.MusicEntity;
import com.example.musicsystem.repositary.MusicRepo;
import com.example.musicsystem.requestDto.ConversationDto;
import com.example.musicsystem.requestDto.SessionDto;
import com.example.musicsystem.responseDto.ApiResponse;
import com.example.musicsystem.service.MusicService;
import com.example.musicsystem.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;
import com.google.actions.api.Capability;
import com.google.actions.api.response.ResponseBuilder;
import com.google.api.services.actions_fulfillment.v2.model.*;
import com.google.gson.JsonObject;
import com.google.gson.Gson;
import okhttp3.*;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

public class MusicServiceimpl implements MusicService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MusicApplication.class);
    private final GlobalEntity globalEntity = new GlobalEntity();

    private final Utils generalFunctions = new Utils();

    String imgUrl = "https://storage.googleapis.com/automotive-media/album_art.jpg";
    String linkoutSuggestions = "https://assistant.google.com/";

    @Override
    public ActionResponse welcome(ActionRequest req)  {
        globalEntity.setSelectedWay(true);
        ConversationDto conversationDto = null;
        MusicRepo musicRepo = new MusicRepo();
        try {
            conversationDto = new ObjectMapper().readValue(new Gson().toJson(req.getAppRequest().get("conversation")), ConversationDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        LOGGER.info("conversationDto : {}", conversationDto);
        globalEntity.setConversionId(conversationDto.getConversationId());
        SessionDto sessionDto = musicRepo.sessionResponse(req, globalEntity,false);
        ResponseBuilder resp = new ResponseBuilder();
        ResourceBundle rd = ResourceBundle.getBundle("resources");
        globalEntity.setUserFlag(true);
        resp.add(rd.getString("welcome"));
        return resp.build();
    }

    @Override
    public ActionResponse askName(ActionRequest req, GlobalEntity entity) {
        ResponseBuilder resp = new ResponseBuilder();
        MusicRepo musicRepo = new MusicRepo();
        LOGGER.info("getConversationData : {}", req.getConversationData());
        if (!req.hasCapability(Capability.SCREEN_OUTPUT.getValue())) {
            SessionDto sessionResponse = musicRepo.getSessionResponse(globalEntity.getConversionId());
            musicRepo.updateSessionResponse(req, globalEntity, false,sessionResponse);
            return resp
                    .add("Sorry, try ths on a screen device or select the phone surface in the simulator.")
                    .add("Which response would you like to see next?")
                    .build();
        }
        LOGGER.info("person :{} ",globalEntity.getUserFlag());
        LOGGER.info("Person : {}", req.getParameter("given-name"));
        SessionDto sessionResponse = musicRepo.getSessionResponse(globalEntity.getConversionId());
        musicRepo.updateSessionResponse(req, globalEntity, false,sessionResponse);
        if (globalEntity.getUserFlag() || entity.getUserFlag()) {
            globalEntity.setSelectedWay(true);
            LOGGER.info("Person : {}", req.getRawText());
            globalEntity.setArtistNameFlag(true);
            globalEntity.setUserName((String) req.getParameter("given-name"));
            if (globalEntity.getUserName() != null) {
                resp.add("Hello " + globalEntity.getUserName() + ", What way to play music?")
                        .addSuggestions(new String[]{"Typing", "Options", "Genre"})
                        .add(new LinkOutSuggestion().setDestinationName("Google Assistant").setUrl(linkoutSuggestions));
            } else {
                resp.add("Welcome Back,  What way to play Music?")
                        .addSuggestions(new String[]{"Typing", "Options", "Genre"})
                        .add(new LinkOutSuggestion().setDestinationName("Google Assistant").setUrl(linkoutSuggestions));
            }
            LOGGER.info("global entity from askname : {}",globalEntity);
            globalEntity.setUserFlag(false);
        }
        return resp.build();
    }

    @Override
    public ActionResponse typeToPlay(ActionRequest req, GlobalEntity gbEntity) {
        LOGGER.info("gbentity from typeToPlay : {}",gbEntity);
        LOGGER.info("global entity from typeToPlay : {}",globalEntity);
        LOGGER.info("one Flag: {}", gbEntity.getPaginationOneFlag());
        LOGGER.info("two Flag: {}", gbEntity.getPaginationTwoFlag());
        LOGGER.info("three Flag: {}", gbEntity.getPaginationThreeFlag());
        LOGGER.info("options Flag in serviceImpl: {}", req.getParameter("optionFlag"));
        ResponseBuilder resp = new ResponseBuilder();
        ResourceBundle rd = ResourceBundle.getBundle("resources");
        LOGGER.info("options flag Entity: {}",Boolean.TRUE.equals(gbEntity.getOptionFlag()));
        LOGGER.info(": {}",Boolean.TRUE.equals(Boolean.TRUE.equals(globalEntity.getArtistNameFlag())));
        LOGGER.info(": {}",gbEntity.getPaginationOneFlag());
        LOGGER.info(": {}",Objects.equals(req.getParameter("optionFlag"),"options"));
        LOGGER.info("option flag :{} ", Boolean.TRUE.equals(gbEntity.getFallbackTypingFlag()));
        MusicRepo musicRepo = new MusicRepo();
        SessionDto sessionResponse = musicRepo.getSessionResponse(globalEntity.getConversionId());
        musicRepo.updateSessionResponse(req, globalEntity, false,sessionResponse);
        if (Objects.equals(req.getParameter("optionFlag"), "typing") && Boolean.TRUE.equals(globalEntity.getArtistNameFlag())) {
            resp.add(rd.getString("typeToPlay"));
        }
        else if (Objects.equals(req.getParameter("optionFlag"), "options") &&Boolean.TRUE.equals(gbEntity.getOptionFlag()) && Boolean.TRUE.equals(globalEntity.getArtistNameFlag())) {
            if (gbEntity.getPaginationOneFlag()) {
                LOGGER.info("one Flag: {}", gbEntity.getPaginationOneFlag());
                String exampleSsml;
                if (globalEntity.getUserName() != null) {
                    exampleSsml = generalFunctions.convertSSMLSpeech(rd.getString("firstResponse"),0.5, globalEntity.getUserName());
                } else {
                    exampleSsml = generalFunctions.convertSSMLSpeech(rd.getString("repeatArtist"));
                }
                SimpleResponse ssmlResp = new SimpleResponse().setSsml(exampleSsml);
                resp.add(ssmlResp)
                        .addSuggestions(new String[]{"Arijit sigh", "Martin Garrix", "Imagine Dragons", "Next"})
                        .add(new LinkOutSuggestion().setDestinationName("Artist Name").setUrl(linkoutSuggestions));
            } else if (gbEntity.getPaginationTwoFlag()) {
                LOGGER.info("two Flag: {}", gbEntity.getPaginationTwoFlag());
                String exampleSsml = generalFunctions.convertSSMLSpeech(rd.getString("secondResponse"));
                SimpleResponse ssmlResp = new SimpleResponse().setSsml(exampleSsml);
                resp.add(ssmlResp)
                        .addSuggestions(new String[]{"Glass Animals", "KK", "Lucky Ali", "Next"})
                        .add(new LinkOutSuggestion().setDestinationName("Artist Name").setUrl(linkoutSuggestions));
            } else if (gbEntity.getPaginationThreeFlag()) {
                LOGGER.info("three Flag: {}", gbEntity.getPaginationThreeFlag());
                String exampleSsml = generalFunctions.convertSSMLSpeech(rd.getString("thirdResponse"));
                SimpleResponse ssmlResp = new SimpleResponse().setSsml(exampleSsml);
                resp.add(ssmlResp)
                        .addSuggestions(new String[]{"Ranbir Kapor", "Arjun Rampal"})
                        .add(new LinkOutSuggestion().setDestinationName("Artist Name").setUrl(linkoutSuggestions));
            }
        }
        else if (Objects.equals(req.getParameter("optionFlag"), "Genere") && Boolean.TRUE.equals(globalEntity.getArtistNameFlag())) {
            LOGGER.info("genere from service");
            globalEntity.setPlayGenereFlag(true);
            String exampleSsml;
            if (globalEntity.getUserName() != null) {
                exampleSsml = generalFunctions.convertSSMLSpeech(rd.getString("genereResponse"),0.5, globalEntity.getUserName());
            } else {
                exampleSsml = generalFunctions.convertSSMLSpeech(rd.getString("repeatGenereResponse"));
            }
            SimpleResponse ssmlResp = new SimpleResponse().setSsml(exampleSsml);
            resp.add(ssmlResp)
                    .addSuggestions(new String[]{"pop", "rock", "romantic", "random"})
                    .add(new LinkOutSuggestion().setDestinationName("Genere Name").setUrl(linkoutSuggestions));
        }
        globalEntity.setSelectedWay(false);
        return resp.build();
    }

    @Override
    public ActionResponse playSong(ActionRequest req) {
        LOGGER.info("option flag : {}", req.getParameter("songGenere"));
        ResponseBuilder resp = new ResponseBuilder();
        LOGGER.info("play songs artist : {}", req.getParameter("artistName"));
        LOGGER.info("play song : {}", req.getAppRequest());
        MusicRepo musicRepo = new MusicRepo();
        SessionDto sessionResponse = musicRepo.getSessionResponse(globalEntity.getConversionId());
        musicRepo.updateSessionResponse(req, globalEntity, false,sessionResponse);
        if (globalEntity.getPlayGenereFlag()) {
            if (Objects.equals(req.getParameter("songGenere"), "pop")) {
                MusicRepo repo = new MusicRepo();
                MusicEntity entity = repo.musicResponse(req);
                System.out.println("genere list from back end = " + new Gson().toJson(entity));
                resp = generalFunctions.playMediaMusic(req, entity);
            } else if (Objects.equals(req.getParameter("songGenere"), "random")) {
                MusicRepo repo = new MusicRepo();
                MusicEntity entity = repo.musicResponse(req);
                System.out.println("genere list from back end = " + new Gson().toJson(entity));
                resp = generalFunctions.playMediaMusic(req, entity);
            } else if (Objects.equals(req.getParameter("songGenere"), "rock")) {
                MusicRepo repo = new MusicRepo();
                MusicEntity entity = repo.musicResponse(req);
                System.out.println("genere list from back end = " + new Gson().toJson(entity));
                resp = generalFunctions.playMediaMusic(req, entity);
            } else if (Objects.equals(req.getParameter("songGenere"), "romantic")) {
                MusicRepo repo = new MusicRepo();
                MusicEntity entity = repo.musicResponse(req);
                System.out.println("genere list from back end = " + new Gson().toJson(entity));
                resp = generalFunctions.playMediaMusic(req, entity);
            }
        }
        if (req.getParameter("artistName") != null && (globalEntity.getArtistNameFlag() || globalEntity.getOptionFlag())) {
            if (Objects.equals(req.getParameter("artistName"), "Arijit sigh")) {
                MusicRepo repo = new MusicRepo();
                ArtistEntity entity = repo.artistResponse(req);
                System.out.println("artist = " + new Gson().toJson(entity));
                resp = generalFunctions.playMedia(req, entity);
                globalEntity.setArtistNameFlag(false);
            } else if (Objects.equals(req.getParameter("artistName"), "MArtin Garrix")) {
                MusicRepo repo = new MusicRepo();
                ArtistEntity entity = repo.artistResponse(req);
                System.out.println("artist = " + new Gson().toJson(entity));
                resp = generalFunctions.playMedia(req, entity);
            } else if (Objects.equals(req.getParameter("artistName"), "Imagine Dragons")) {
                MusicRepo repo = new MusicRepo();
                ArtistEntity entity = repo.artistResponse(req);
                System.out.println("artist = " + new Gson().toJson(entity));
                resp = generalFunctions.playMedia(req, entity);
            } else if (Objects.equals(req.getParameter("artistName"), "Glass Animals")) {
                MusicRepo repo = new MusicRepo();
                ArtistEntity entity = repo.artistResponse(req);
                System.out.println("artist = " + new Gson().toJson(entity));
                resp = generalFunctions.playMedia(req, entity);
            } else if (Objects.equals(req.getParameter("artistName"), "KK")) {
                MusicRepo repo = new MusicRepo();
                ArtistEntity entity = repo.artistResponse(req);
                System.out.println("artist = " + new Gson().toJson(entity));
                resp = generalFunctions.playMedia(req, entity);
            } else if (Objects.equals(req.getParameter("artistName"), "Lucky Ali")) {
                MusicRepo repo = new MusicRepo();
                ArtistEntity entity = repo.artistResponse(req);
                System.out.println("artist = " + new Gson().toJson(entity));
                resp = generalFunctions.playMedia(req, entity);
            } else if (Objects.equals(req.getParameter("artistName"), "Ranbir Kapor")) {
                MusicRepo repo = new MusicRepo();
                ArtistEntity entity = repo.artistResponse(req);
                System.out.println("artist = " + new Gson().toJson(entity));
                resp = generalFunctions.playMedia(req, entity);
            } else if (Objects.equals(req.getParameter("artistName"), "Arjun Rampal")) {
                MusicRepo repo = new MusicRepo();
                ArtistEntity entity = repo.artistResponse(req);
                System.out.println("artist = " + new Gson().toJson(entity));
                resp = generalFunctions.playMedia(req, entity);
            }
        }
        return resp.build();
    }

    @Override
    public ActionResponse mediaStatus(ActionRequest req) {
        ResponseBuilder resp = new ResponseBuilder();
        ResourceBundle rd  = ResourceBundle.getBundle("messages");
        LOGGER.info("play songs : {}", req.getParameter("artistName"));
        LOGGER.info("option flag : {}", globalEntity.getOptionFlag());
        LOGGER.info("artist flag : {}", globalEntity.getArtistNameFlag());
        MusicRepo musicRepo = new MusicRepo();
        SessionDto sessionResponse = musicRepo.getSessionResponse(globalEntity.getConversionId());
        musicRepo.updateSessionResponse(req, globalEntity, false,sessionResponse);
        if (Boolean.TRUE.equals(globalEntity.getArtistNameFlag() || globalEntity.getOptionFlag() || globalEntity.getPlayGenereFlag())) {
            LOGGER.info("Media Status: {}", req.getMediaStatus());
            String mediaStatus = req.getMediaStatus();
            LOGGER.info("Media Status : {}", mediaStatus);
            if (mediaStatus != null && mediaStatus.equals("FINISHED")) {
                if (globalEntity.getOptionFlag()) {
                    globalEntity.setOptionFlag(false);
                }
                if (globalEntity.getArtistNameFlag()) {
                    globalEntity.setArtistNameFlag(false);
                }
                if (globalEntity.getPlayGenereFlag()) {
                    globalEntity.setPlayGenereFlag(false);
                }
                resp.add(rd.getString("songComplete"))
                        .addSuggestions(new String[]{"Next"})
                        .add(new LinkOutSuggestion().setDestinationName("Artist Name").setUrl(linkoutSuggestions));
            } else if (mediaStatus != null && mediaStatus.equals("FAILED")) {
                globalEntity.setArtistNameFlag(false);
                resp.add("Sorry, I can't play this song.")
                        .addSuggestions(new String[]{"Back"})
                        .add(new LinkOutSuggestion().setDestinationName("Artist Name").setUrl(linkoutSuggestions));
            }
        }
        return resp.build();
    }

    @Override
    public ActionResponse mediaStatusNext(ActionRequest req) {
        ResponseBuilder resp = new ResponseBuilder();
        ResourceBundle rd = ResourceBundle.getBundle("resources");
        MusicRepo musicRepo = new MusicRepo();
        SessionDto sessionResponse = musicRepo.getSessionResponse(globalEntity.getConversionId());
        musicRepo.updateSessionResponse(req, globalEntity, false,sessionResponse);
        resp.add(rd.getString("nextResponse")).addSuggestions(new String[]{"Yes", "No"})
                .add(
                        new LinkOutSuggestion()
                                .setDestinationName("Artist Name")
                                .setUrl(linkoutSuggestions));
        return resp.build();
    }

    @Override
    public ActionResponse noResponse(ActionRequest req) {
        ResponseBuilder resp = new ResponseBuilder();
        ResourceBundle rd = ResourceBundle.getBundle("messages");
        MusicRepo musicRepo = new MusicRepo();
        SessionDto sessionResponse = musicRepo.getSessionResponse(globalEntity.getConversionId());
        musicRepo.updateSessionResponse(req, globalEntity, false,sessionResponse);
        resp.add(rd.getString("noNextResponse")).addSuggestions(new String[]{"Yes", "No"})
                .add(
                        new LinkOutSuggestion()
                                .setDestinationName("Artist Name")
                                .setUrl(linkoutSuggestions));
        return resp.build();
    }

    @Override
    public ActionResponse yesReviewResponse(ActionRequest req) {
        ResponseBuilder resp = new ResponseBuilder();
        ResourceBundle rd = ResourceBundle.getBundle("messages");
        MusicRepo musicRepo = new MusicRepo();
        SessionDto sessionResponse = musicRepo.getSessionResponse(globalEntity.getConversionId());
        musicRepo.updateSessionResponse(req, globalEntity, false,sessionResponse);
        resp.add(rd.getString("yesReviewResponse")).endConversation();
        return resp.build();
    }

    @Override
    public ActionResponse noReviewResponse(ActionRequest req) {
        ResponseBuilder resp = new ResponseBuilder();
        ResourceBundle rd = ResourceBundle.getBundle("messages");
        MusicRepo musicRepo = new MusicRepo();
        SessionDto sessionResponse = musicRepo.getSessionResponse(globalEntity.getConversionId());
        musicRepo.updateSessionResponse(req, globalEntity, false,sessionResponse);
        resp.add(rd.getString("noReviewResponse")).endConversation();
        return resp.build();
    }

    @Override
    public ActionResponse fallBack(ActionRequest req) {

        ResponseBuilder resp = new ResponseBuilder();
        LOGGER.info("OptionFlagName: {}",req.getParameter("optionFlag"));
        LOGGER.info("artistName: {}",req.getParameter("artistName"));
        LOGGER.info("next: {}",req.getParameter("next"));
        String artistName = (String) req.getParameter("artistName");
        String songGenere = (String) req.getParameter("songGenere");
        ResourceBundle rb = ResourceBundle.getBundle("resources");
        String[] artistNames = {"Arijit sigh", "MArtin Garrix", "Imagine Dragons"};
        String[] songGeneres = {"pop", "rock", "romantic", "random"};
        Boolean artistNameFlag = globalEntity.getArtistNameFlag();
        MusicRepo musicRepo = new MusicRepo();
        SessionDto sessionResponse = musicRepo.getSessionResponse(globalEntity.getConversionId());
        musicRepo.updateSessionResponse(req, globalEntity, true,sessionResponse);
        LOGGER.info("selectdway : {}", globalEntity.getSelectedWay());
        if (globalEntity.getUserFlag()) {
            /**
             * If user name
             */
            resp.add(rb.getString("notFoundName"));
        } else if (globalEntity.getSelectedWay()) {
            /**
             * If user type other name
             */
            String exampleSsml = generalFunctions.convertSSMLSpeech(rb.getString("notFoundWay"));
            SimpleResponse ssmlResp = new SimpleResponse().setSsml(exampleSsml);
            resp.add(ssmlResp)
                    .addSuggestions(new String[]{"Typing", "Options", "Genre"})
                    .add(new LinkOutSuggestion().setDestinationName("Genere").setUrl(linkoutSuggestions));
        } else if (!Arrays.asList(artistNames).contains(artistName) && globalEntity.getArtistNameFlag() && !globalEntity.getPlayGenereFlag()) {
            /**
             * If user type other Artist Name
             */
            LOGGER.info("artistNameFlag: {}", artistNameFlag);
            String exampleSsml = generalFunctions.convertSSMLSpeech(rb.getString("notFoundArtist"));
            SimpleResponse ssmlResp = new SimpleResponse().setSsml(exampleSsml);
            resp.add(ssmlResp).addSuggestions(artistNames)
                    .add(new LinkOutSuggestion().setDestinationName("Artist Name").setUrl(linkoutSuggestions));
        } else if (!Arrays.asList(songGeneres).contains(songGenere) && globalEntity.getPlayGenereFlag()) {
            /**
             * If user type other Genere
             */
            String exampleSsml = generalFunctions.convertSSMLSpeech(rb.getString("notFoundGenere"));
            SimpleResponse ssmlResp = new SimpleResponse().setSsml(exampleSsml);
            resp.add(ssmlResp)
                    .addSuggestions(new String[]{"pop", "rock", "romantic", "random"})
                    .add(new LinkOutSuggestion().setDestinationName("Genere").setUrl(linkoutSuggestions));
        } else if (globalEntity.getUserFlag()) {
            /**
             * If user type other name
             */
            String exampleSsml = generalFunctions.convertSSMLSpeech(rb.getString("notFoundUser"));
            SimpleResponse ssmlResp = new SimpleResponse().setSsml(exampleSsml);
            resp.add(ssmlResp);
        } else {
            /**
             * if not found anything
             */
            ResourceBundle rd = ResourceBundle.getBundle("resources");
            resp.add(rd.getString("fallback"));
        }
        return resp.build();
    }
}
