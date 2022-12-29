package com.example.musicsystem.serviceImpl;

import com.example.musicsystem.MusicApplication;
import com.example.musicsystem.entity.ArtistEntity;
import com.example.musicsystem.entity.GlobalEntity;
import com.example.musicsystem.entity.MusicEntity;
import com.example.musicsystem.service.MusicService;
import com.example.musicsystem.utils.utils;
import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;
import com.google.actions.api.Capability;
import com.google.actions.api.response.ResponseBuilder;
import com.google.api.services.actions_fulfillment.v2.model.*;
import com.google.gson.internal.LinkedTreeMap;
import org.omg.CORBA.Object;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class MusicServiceimpl implements MusicService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MusicApplication.class);
    private final GlobalEntity globalEntity = new GlobalEntity();
    private final utils generalFunctions = new utils();

    String imgUrl = "https://storage.googleapis.com/automotive-media/album_art.jpg";
    String linkoutSuggestions = "https://assistant.google.com/";

    @Override
    public ActionResponse welcome(ActionRequest req) {
        ResponseBuilder resp = new ResponseBuilder();
        ResourceBundle rd = ResourceBundle.getBundle("resources");
        globalEntity.setUserFlag(true);
        resp.add(rd.getString("welcome"));
        globalEntity.setSelectedWay(true);
        return resp.build();
    }

    @Override
    public ActionResponse askName(ActionRequest req, GlobalEntity entity) {
        ResponseBuilder resp = new ResponseBuilder();
        if (!req.hasCapability(Capability.SCREEN_OUTPUT.getValue())) {
            return resp
                    .add("Sorry, try ths on a screen device or select the phone surface in the simulator.")
                    .add("Which response would you like to see next?")
                    .build();
        }
        LOGGER.info("person :{} ",globalEntity.getUserFlag());
        LOGGER.info("Person : {}", req.getParameter("given-name"));
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

            globalEntity.setUserFlag(false);
        }
        return resp.build();
    }

    @Override
    public ActionResponse typeToPlay(ActionRequest req, GlobalEntity gbEntity) {
        LOGGER.info("one Flag: {}", gbEntity.getPaginationOneFlag());
        LOGGER.info("two Flag: {}", gbEntity.getPaginationTwoFlag());
        LOGGER.info("three Flag: {}", gbEntity.getPaginationThreeFlag());
        LOGGER.info("Raw Text: {}", req.getRawText());
        LOGGER.info("status: {}", req.getParameter("optionFlag"));
        ResponseBuilder resp = new ResponseBuilder();
        ResourceBundle rd = ResourceBundle.getBundle("resources");
        LOGGER.info(": {}",Boolean.TRUE.equals(gbEntity.getOptionFlag()) && Boolean.TRUE.equals(globalEntity.getArtistNameFlag()));
        LOGGER.info(": {}",gbEntity.getPaginationOneFlag());
        LOGGER.info(": {}",Objects.equals(req.getParameter("optionFlag"),"options"));
        if (Objects.equals(req.getParameter("optionFlag"), "typing") && Boolean.TRUE.equals(globalEntity.getArtistNameFlag())) {
            resp.add(rd.getString("typeToPlay"));
        }
        else if (Boolean.TRUE.equals(gbEntity.getOptionFlag()) && Boolean.TRUE.equals(globalEntity.getArtistNameFlag())) {
            if (gbEntity.getPaginationOneFlag()) {
                LOGGER.info("one Flag: {}", gbEntity.getPaginationOneFlag());
                String exampleSsml;
                LOGGER.info("repeat Flag: {}", gbEntity.getRepeatFlag());
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
            globalEntity.setPlayGenereFlag(true);
            resp.add("Hello " + globalEntity.getUserName() + ", What Music Genre you want to listen?")
                    .addSuggestions(new String[]{"pop", "rock", "romantic", "random"})
                    .add(new LinkOutSuggestion().setDestinationName("Genere").setUrl(linkoutSuggestions));
        }
        globalEntity.setSelectedWay(false);
        return resp.build();
    }

    @Override
    public ActionResponse playSong(ActionRequest req) {
        List<ArtistEntity> artist = new ArrayList();
        artist.add(new ArtistEntity("Arijit sigh", "https://demo7uyidtyietyuiet.s3.ap-south-1.amazonaws.com/Kesariya+-+Brahm%C4%81stra++Ranbir+Kapoor++Alia+Bhatt++Pritam++Arijit+Singh++Amitabh+Bhattacharya.mp3"));
        artist.add(new ArtistEntity("MArtin Garrix", "https://demo7uyidtyietyuiet.s3.ap-south-1.amazonaws.com/Martin+Garrix+-+Animals+(Official+Video).mp3"));
        artist.add(new ArtistEntity("Imagine Dragons", "https://demo7uyidtyietyuiet.s3.ap-south-1.amazonaws.com/Imagine+Dragons+-+Thunder.mp3"));
        artist.add(new ArtistEntity("Glass Animals", "https://demo7uyidtyietyuiet.s3.ap-south-1.amazonaws.com/Imagine+Dragons+-+Thunder.mp3"));
        artist.add(new ArtistEntity("KK", "https://demo7uyidtyietyuiet.s3.ap-south-1.amazonaws.com/KK+-+Yaaron.mp3"));
        artist.add(new ArtistEntity("Lucky Ali", "https://demo7uyidtyietyuiet.s3.ap-south-1.amazonaws.com/O+Sanam+-+Sunoh++Lucky+Ali++(Official+Video).mp3"));
        artist.add(new ArtistEntity("Ranbir Kapor", "https://demo7uyidtyietyuiet.s3.ap-south-1.amazonaws.com/Sadda+Haq+Full+Video+Song+Rockstar++Ranbir+Kapoor.mp3"));
        artist.add(new ArtistEntity("Arjun Rampal", "https://demo7uyidtyietyuiet.s3.ap-south-1.amazonaws.com/Rock+On+Title+Video+Song++Arjun+Rampal%2C+Farhan+Akhtar%2C+Prachi+Desai%2C+Purab+Kohli%2C+Koel+Puri.mp3"));
        List<MusicEntity> rocks = new ArrayList<>();
        rocks.add(new MusicEntity(1,
                "https://demo7uyidtyietyuiet.s3.ap-south-1.amazonaws.com/Rock+On+Title+Video+Song++Arjun+Rampal%2C+Farhan+Akhtar%2C+Prachi+Desai%2C+Purab+Kohli%2C+Koel+Puri.mp3",
                "Rock on", imgUrl, "Rock on"));

        rocks.add(new MusicEntity(2,
                "https://demo7uyidtyietyuiet.s3.ap-south-1.amazonaws.com/Sadda+Haq+Full+Video+Song+Rockstar++Ranbir+Kapoor.mp3",
                "Sadda Haq", imgUrl, "Sadda Haq"));

        List<MusicEntity> pops = new ArrayList<>();
        pops.add(new MusicEntity(1,
                "https://demo7uyidtyietyuiet.s3.ap-south-1.amazonaws.com/KK+-+Yaaron.mp3",
                "Yaaron", imgUrl, "Yaaron"));
        pops.add(new MusicEntity(2,
                "https://demo7uyidtyietyuiet.s3.ap-south-1.amazonaws.com/O+Sanam+-+Sunoh++Lucky+Ali++(Official+Video).mp3",
                "O Sama", imgUrl, "Channa Mereya"));

        List<MusicEntity> romantics = new ArrayList();
        romantics.add(new MusicEntity(1,
                "https://demo7uyidtyietyuiet.s3.ap-south-1.amazonaws.com/Kesariya+-+Brahm%C4%81stra++Ranbir+Kapoor++Alia+Bhatt++Pritam++Arijit+Singh++Amitabh+Bhattacharya.mp3",
                "Kesariya", imgUrl, "Kesariya"));
        romantics.add(new MusicEntity(2,
                "https://demo7uyidtyietyuiet.s3.ap-south-1.amazonaws.com/Imagine+Dragons+-+Thunder.mp3",
                "Martin Garrix", imgUrl, "Martin Garrix"));
        List<MusicEntity> randoms = new ArrayList<>();
        randoms.add(new MusicEntity(1,
                "https://demo7uyidtyietyuiet.s3.ap-south-1.amazonaws.com/Rock+On+Title+Video+Song++Arjun+Rampal%2C+Farhan+Akhtar%2C+Prachi+Desai%2C+Purab+Kohli%2C+Koel+Puri.mp3",
                "Rock on", imgUrl, "Rock on"));
        randoms.add(new MusicEntity(2,
                "https://demo7uyidtyietyuiet.s3.ap-south-1.amazonaws.com/Sadda+Haq+Full+Video+Song+Rockstar++Ranbir+Kapoor.mp3",
                "Sadda Haq", imgUrl, "Ranbir Kapoor"));

        randoms.add(new MusicEntity(3,
                "https://demo7uyidtyietyuiet.s3.ap-south-1.amazonaws.com/KK+-+Yaaron.mp3",
                "Yaaron", imgUrl, "Yaaron"));
        randoms.add(new MusicEntity(4,
                "https://demo7uyidtyietyuiet.s3.ap-south-1.amazonaws.com/O+Sanam+-+Sunoh++Lucky+Ali++(Official+Video).mp3",
                "O Sama", imgUrl, "Channa Mereya"));

        randoms.add(new MusicEntity(5,
                "https://demo7uyidtyietyuiet.s3.ap-south-1.amazonaws.com/Kesariya+-+Brahm%C4%81stra++Ranbir+Kapoor++Alia+Bhatt++Pritam++Arijit+Singh++Amitabh+Bhattacharya.mp3",
                "Kesariya", imgUrl, "Arijit Sigh"));

        randoms.add(new MusicEntity(6,
                "https://demo7uyidtyietyuiet.s3.ap-south-1.amazonaws.com/Martin+Garrix+-+Animals+(Official+Video).mp3",
                "Martin Garrix", imgUrl, "Martin Garrix"));
        randoms.add(new MusicEntity(7,
                "https://demo7uyidtyietyuiet.s3.ap-south-1.amazonaws.com/Imagine+Dragons+-+Thunder.mp3",
                "Imagine Dragons", imgUrl, "Imagine Dragons"));
        LOGGER.info("option flag : {}", req.getParameter("songGenere"));
        ResponseBuilder resp = new ResponseBuilder();
        LOGGER.info("play songs artist : {}", req.getParameter("artistName"));
        LOGGER.info("play song : {}", req.getAppRequest());
        if (globalEntity.getPlayGenereFlag()) {
            if (Objects.equals(req.getParameter("songGenere"), "pop")) {
                resp = generalFunctions.playMediaGenere(req, pops);
            } else if (Objects.equals(req.getParameter("songGenere"), "random")) {
                LOGGER.info("song genere : {}", req.getParameter("songGenere"));
                resp = generalFunctions.playMediaGenere(req, randoms);
            } else if (Objects.equals(req.getParameter("songGenere"), "rock")) {
                resp = generalFunctions.playMediaGenere(req, rocks);
            } else if (Objects.equals(req.getParameter("songGenere"), "romantic")) {
                resp = generalFunctions.playMediaGenere(req, romantics);
            }
        }
        if (req.getParameter("artistName") != null && (globalEntity.getArtistNameFlag() || globalEntity.getOptionFlag())) {
            if (Objects.equals(req.getParameter("artistName"), "Arijit sigh")) {
                resp = generalFunctions.playMedia(req, artist.get(0));
                globalEntity.setArtistNameFlag(false);
            } else if (Objects.equals(req.getParameter("artistName"), "MArtin Garrix")) {
                resp = generalFunctions.playMedia(req, artist.get(1));
            } else if (Objects.equals(req.getParameter("artistName"), "Imagine Dragons")) {
                resp = generalFunctions.playMedia(req, artist.get(2));
            } else if (Objects.equals(req.getParameter("artistName"), "Glass Animals")) {
                resp = generalFunctions.playMedia(req, artist.get(3));
            } else if (Objects.equals(req.getParameter("artistName"), "KK")) {
                resp = generalFunctions.playMedia(req, artist.get(4));
            } else if (Objects.equals(req.getParameter("artistName"), "Lucky Ali")) {
                resp = generalFunctions.playMedia(req, artist.get(5));
            } else if (Objects.equals(req.getParameter("artistName"), "Ranbir Kapor")) {
                resp = generalFunctions.playMedia(req, artist.get(6));
            } else if (Objects.equals(req.getParameter("artistName"), "Arjun Rampal")) {
                resp = generalFunctions.playMedia(req, artist.get(7));
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
        resp.add(rd.getString("yesReviewResponse")).endConversation();
        return resp.build();
    }

    @Override
    public ActionResponse noReviewResponse(ActionRequest req) {
        ResponseBuilder resp = new ResponseBuilder();
        ResourceBundle rd = ResourceBundle.getBundle("messages");
        resp.add(rd.getString("noReviewResponse")).endConversation();
        return resp.build();
    }

    @Override
    public ActionResponse fallBack(ActionRequest req) {

        ResponseBuilder resp = new ResponseBuilder();
        LOGGER.info("Fallback : {}", req.getAppRequest());
        LOGGER.info("Parameter Text: {}", req.getParameter("artistName"));
        String artistName = (String) req.getParameter("artistName");
        String songGenere = (String) req.getParameter("songGenere");
        ResourceBundle rb = ResourceBundle.getBundle("resources");
        String[] artistNames = {"Arijit sigh", "MArtin Garrix", "Imagine Dragons"};
        String[] songGeneres = {"pop", "rock", "romantic", "random"};
        Boolean artistNameFlag = globalEntity.getArtistNameFlag();

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
