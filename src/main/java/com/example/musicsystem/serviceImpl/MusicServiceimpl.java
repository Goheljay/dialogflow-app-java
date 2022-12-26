package com.example.musicsystem.serviceImpl;

import com.example.musicsystem.MusicApplication;
import com.example.musicsystem.entity.GlobalEntity;
import com.example.musicsystem.entity.MusicEntity;
import com.example.musicsystem.service.MusicService;
import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;
import com.google.actions.api.Capability;
import com.google.actions.api.response.ResponseBuilder;
import com.google.api.services.actions_fulfillment.v2.model.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class MusicServiceimpl implements MusicService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MusicApplication.class);
    private final GlobalEntity globalEntity = new GlobalEntity();

    String imgUrl = "https://storage.googleapis.com/automotive-media/album_art.jpg";
    String linkoutSuggestions = "https://assistant.google.com/";

    @Override
    public ActionResponse welcome(ActionRequest req) {
        ResponseBuilder resp = new ResponseBuilder();
        ResourceBundle rd = ResourceBundle.getBundle("resources");
        globalEntity.setUserFlag(true);
        resp.add(rd.getString("welcome"));
        return resp.build();
    }

    @Override
    public ActionResponse askName(ActionRequest req) {
        ResponseBuilder resp = new ResponseBuilder();
        if (!req.hasCapability(Capability.SCREEN_OUTPUT.getValue())) {
            return resp
                    .add("Sorry, try ths on a screen device or select the phone surface in the simulator.")
                    .add("Which response would you like to see next?")
                    .build();
        }
        if (Boolean.TRUE.equals(globalEntity.getUserFlag())) {
            LOGGER.info("Person : {}", req.getParameter("given-name"));
            globalEntity.setUserName((String) req.getParameter("given-name"));
            globalEntity.setArtistNameFlag(true);
            resp.add("Hello " + globalEntity.getUserName() + ", What way to play music?")
                    .addSuggestions(new String[]{"Typing", "Options", "Genre"})
                    .add(new LinkOutSuggestion().setDestinationName("Google Assistant").setUrl(linkoutSuggestions));
            globalEntity.setUserFlag(false);
        }
        return resp.build();
    }

    @Override
    public ActionResponse typeToPlay(ActionRequest req) {
        ResponseBuilder resp = new ResponseBuilder();
        LOGGER.info("Raw Text: {}", req.getRawText());
        LOGGER.info("status: {}", req.getParameter("optionFlag"));
        ResourceBundle rd = ResourceBundle.getBundle("resources");
        if (Objects.equals(req.getParameter("optionFlag"), "typing") && Boolean.TRUE.equals(globalEntity.getArtistNameFlag())) {
            resp.add(rd.getString("typeToPlay"));
        } else if (Objects.equals(req.getParameter("optionFlag"), "options") && Boolean.TRUE.equals(globalEntity.getArtistNameFlag())) {
            resp.add("Hello " + globalEntity.getUserName() + ", What Music Artist you want to listen?")
                    .addSuggestions(new String[]{"Arijit sigh", "Martin Garrix", "Imagine Dragons"})
                    .add(new LinkOutSuggestion().setDestinationName("Artist Name").setUrl(linkoutSuggestions));
        } else if (Objects.equals(req.getParameter("optionFlag"), "Genere") && Boolean.TRUE.equals(globalEntity.getArtistNameFlag())) {
            globalEntity.setPlayTypeFlag(true);
            resp.add("Hello " + globalEntity.getUserName() + ", What Music Genre you want to listen?")
                    .addSuggestions(new String[]{"pop", "rock", "romantic", "random"})
                    .add(new LinkOutSuggestion().setDestinationName("Genere").setUrl(linkoutSuggestions));
        }
        return resp.build();
    }

    @Override
    public ActionResponse songGenere(ActionRequest req) {

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
        if (Objects.equals(req.getParameter("songGenere"), "pop")) {
            int num = (int) (Math.random() * (1 - (pops.size() == 0 ? 1 : pops.size())));
            if (num == 0) {
                num = 1;
            }
            LOGGER.info("Random number: {}", num);
            int finalNum = num;
            pops.forEach(pop -> {
                if (pop.getId() == finalNum) {
                    resp.add("Playing " + req.getParameter("songGenere") + " Genere");
                    resp.addSuggestions(new String[]{"Okay", "Cancel", "Next"});
                    resp.add(
                            new MediaResponse()
                                    .setMediaObjects(
                                            Collections.singletonList(
                                                    new MediaObject()
                                                            .setName(pop.getSong_name())
                                                            .setDescription(pop.getArtist_name())
                                                            .setContentUrl(pop.getAudio_link())
                                                            .setIcon(new Image()
                                                                    .setUrl(imgUrl)
                                                                    .setAccessibilityText("Album cover of an ocean view"))))
                                    .setMediaType("AUDIO"));
                }
            });
        }
        else if (Objects.equals(req.getParameter("songGenere"), "random")) {
            int num = (int) (Math.random() * randoms.size());
            LOGGER.info("Random number: {}", num);
            if (num == 0) {
                num = 1;
            }
            LOGGER.info("Random number: {}", num);
            int finalNum = num;
            randoms.forEach(random -> {
                if (random.getId() == finalNum) {
                    resp.add("Playing " + req.getParameter("songGenere") + " Genere");
                    resp.addSuggestions(new String[]{"Okay", "Cancel", "Next"});
                    resp.add(
                            new MediaResponse()
                                    .setMediaObjects(
                                            Collections.singletonList(
                                                    new MediaObject()
                                                            .setName(random.getSong_name())
                                                            .setDescription(random.getArtist_name())
                                                            .setContentUrl(random.getAudio_link())
                                                            .setIcon(new Image()
                                                                    .setUrl(imgUrl)
                                                                    .setAccessibilityText("Album cover of an ocean view"))))
                                    .setMediaType("AUDIO"));
                }
            });
        }
        else if (Objects.equals(req.getParameter("songGenere"), "rock")) {
            int num = (int) (Math.random() * (1 - (randoms.size() == 0 ? 1 : rocks.size())));
            if (num == 0) {
                num = 1;
            }
            LOGGER.info("Random number: {}", num);
            int finalNum = num;
            rocks.forEach(rock -> {
                if (rock.getId() == finalNum) {
                    resp.add("Playing " + req.getParameter("songGenere") + " Genere");
                    resp.addSuggestions(new String[]{"Okay", "Cancel", "Next"});
                    resp.add(
                            new MediaResponse()
                                    .setMediaObjects(
                                            Collections.singletonList(
                                                    new MediaObject()
                                                            .setName(rock.getSong_name())
                                                            .setDescription(rock.getArtist_name())
                                                            .setContentUrl(rock.getAudio_link())
                                                            .setIcon(new Image()
                                                                    .setUrl(imgUrl)
                                                                    .setAccessibilityText("Album cover of an ocean view"))))
                                    .setMediaType("AUDIO"));
                }
            });
        }
        else if (Objects.equals(req.getParameter("songGenere"), "romantic")) {
            int num = (int) (Math.random() * (1 - (romantics.size() == 0 ? 1 : romantics.size())));
            if (num == 0) {
                num = 1;
            }
            LOGGER.info("Random number: {}", num);
            int finalNum = num;
            romantics.forEach(romantic -> {
                if (romantic.getId() == finalNum) {
                    resp.add("Playing " + req.getParameter("songGenere") + " Genere");
                    resp.addSuggestions(new String[]{"Okay", "Cancel", "Next"});
                    resp.add(
                            new MediaResponse()
                                    .setMediaObjects(
                                            Collections.singletonList(
                                                    new MediaObject()
                                                            .setName(romantic.getSong_name())
                                                            .setDescription(romantic.getArtist_name())
                                                            .setContentUrl(romantic.getAudio_link())
                                                            .setIcon(new Image()
                                                                    .setUrl(imgUrl)
                                                                    .setAccessibilityText("Album cover of an ocean view"))))
                                    .setMediaType("AUDIO"));
                }
            });
        }
        return resp.build();
    }

    @Override
    public ActionResponse playSong(ActionRequest req) {
        ResponseBuilder resp = new ResponseBuilder();
        LOGGER.info("play songs : {}", req.getParameter("artistName"));
        LOGGER.info("play song : {}", req.getAppRequest());
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
        if (Objects.equals(req.getParameter("songGenere"), "pop")) {
            int num = (int) (Math.random() * (1 - (pops.size() == 0 ? 1 : pops.size())));
            if (num == 0) {
                num = 1;
            }
            LOGGER.info("Random number: {}", num);
            int finalNum = num;
            pops.forEach(pop -> {
                if (pop.getId() == finalNum) {
                    resp.add("Playing " + req.getParameter("songGenere") + " Genere");
                    resp.addSuggestions(new String[]{"Okay", "Cancel", "Next"});
                    resp.add(
                            new MediaResponse()
                                    .setMediaObjects(
                                            Collections.singletonList(
                                                    new MediaObject()
                                                            .setName(pop.getSong_name())
                                                            .setDescription(pop.getArtist_name())
                                                            .setContentUrl(pop.getAudio_link())
                                                            .setIcon(new Image()
                                                                    .setUrl(imgUrl)
                                                                    .setAccessibilityText("Album cover of an ocean view"))))
                                    .setMediaType("AUDIO"));
                }
            });
        }
        else if (Objects.equals(req.getParameter("songGenere"), "random")) {
            int num = (int) (Math.random() * randoms.size());
            LOGGER.info("Random number: {}", num);
            if (num == 0) {
                num = 1;
            }
            LOGGER.info("Random number: {}", num);
            int finalNum = num;
            randoms.forEach(random -> {
                if (random.getId() == finalNum) {
                    resp.add("Playing " + req.getParameter("songGenere") + " Genere");
                    resp.addSuggestions(new String[]{"Okay", "Cancel", "Next"});
                    resp.add(
                            new MediaResponse()
                                    .setMediaObjects(
                                            Collections.singletonList(
                                                    new MediaObject()
                                                            .setName(random.getSong_name())
                                                            .setDescription(random.getArtist_name())
                                                            .setContentUrl(random.getAudio_link())
                                                            .setIcon(new Image()
                                                                    .setUrl(imgUrl)
                                                                    .setAccessibilityText("Album cover of an ocean view"))))
                                    .setMediaType("AUDIO"));
                }
            });
        }
        else if (Objects.equals(req.getParameter("songGenere"), "rock")) {
            int num = (int) (Math.random() * (1 - (randoms.size() == 0 ? 1 : rocks.size())));
            if (num == 0) {
                num = 1;
            }
            LOGGER.info("Random number: {}", num);
            int finalNum = num;
            rocks.forEach(rock -> {
                if (rock.getId() == finalNum) {
                    resp.add("Playing " + req.getParameter("songGenere") + " Genere");
                    resp.addSuggestions(new String[]{"Okay", "Cancel", "Next"});
                    resp.add(
                            new MediaResponse()
                                    .setMediaObjects(
                                            Collections.singletonList(
                                                    new MediaObject()
                                                            .setName(rock.getSong_name())
                                                            .setDescription(rock.getArtist_name())
                                                            .setContentUrl(rock.getAudio_link())
                                                            .setIcon(new Image()
                                                                    .setUrl(imgUrl)
                                                                    .setAccessibilityText("Album cover of an ocean view"))))
                                    .setMediaType("AUDIO"));
                }
            });
        }
        else if (Objects.equals(req.getParameter("songGenere"), "romantic")) {
            int num = (int) (Math.random() * (1 - (romantics.size() == 0 ? 1 : romantics.size())));
            if (num == 0) {
                num = 1;
            }
            LOGGER.info("Random number: {}", num);
            int finalNum = num;
            romantics.forEach(romantic -> {
                if (romantic.getId() == finalNum) {
                    resp.add("Playing " + req.getParameter("songGenere") + " Genere");
                    resp.addSuggestions(new String[]{"Okay", "Cancel", "Next"});
                    resp.add(
                            new MediaResponse()
                                    .setMediaObjects(
                                            Collections.singletonList(
                                                    new MediaObject()
                                                            .setName(romantic.getSong_name())
                                                            .setDescription(romantic.getArtist_name())
                                                            .setContentUrl(romantic.getAudio_link())
                                                            .setIcon(new Image()
                                                                    .setUrl(imgUrl)
                                                                    .setAccessibilityText("Album cover of an ocean view"))))
                                    .setMediaType("AUDIO"));
                }
            });
        }
        if (req.getParameter("artistName") != null) {
            if (Objects.equals(req.getParameter("artistName"), "Arijit sigh")) {
                resp.add("Playing " + req.getParameter("artistName") + " song");
                resp.addSuggestions(new String[]{"Okay", "Cancel", "Next"});
                resp.add(
                        new MediaResponse()
                                .setMediaObjects(
                                        Collections.singletonList(
                                                new MediaObject()
                                                        .setName("Arijit sigh")
                                                        .setDescription("Arijit sigh")
                                                        .setContentUrl(
                                                                " https://demo7uyidtyietyuiet.s3.ap-south-1.amazonaws.com/Kesariya+-+Brahm%C4%81stra++Ranbir+Kapoor++Alia+Bhatt++Pritam++Arijit+Singh++Amitabh+Bhattacharya.mp3")
                                                        .setIcon(
                                                                new Image()
                                                                        .setUrl(imgUrl)
                                                                        .setAccessibilityText(
                                                                                "Album cover of an ocean view"))))
                                .setMediaType("AUDIO"));

            } else if (Objects.equals(req.getParameter("artistName"), "MArtin Garrix")) {
                resp.add("Playing " + req.getParameter("artistName") + " song");
                resp.addSuggestions(new String[]{"Okay", "Cancel", "Next"});
                resp.add(
                        new MediaResponse()
                                .setMediaObjects(
                                        Collections.singletonList(
                                                new MediaObject()
                                                        .setName("Martin Garrix")
                                                        .setDescription("Martin Garrix")
                                                        .setContentUrl(
                                                                " https://demo7uyidtyietyuiet.s3.ap-south-1.amazonaws.com/Martin+Garrix+-+Animals+(Official+Video).mp3")
                                                        .setIcon(
                                                                new Image()
                                                                        .setUrl(imgUrl)
                                                                        .setAccessibilityText(
                                                                                "Album cover of an ocean view"))))
                                .setMediaType("AUDIO"));
            } else if (Objects.equals(req.getParameter("artistName"), "Imagine Dragons")) {
                resp.add("Playing " + req.getParameter("artistName") + " song");
                resp.addSuggestions(new String[]{"Okay", "Cancel", "Next"});
                resp.add(
                        new MediaResponse()
                                .setMediaObjects(
                                        Collections.singletonList(
                                                new MediaObject()
                                                        .setName("Imagine Dragons")
                                                        .setDescription("Imagine Dragons")
                                                        .setContentUrl("https://demo7uyidtyietyuiet.s3.ap-south-1.amazonaws.com/Imagine+Dragons+-+Thunder.mp3")
                                                        .setIcon(
                                                                new Image()
                                                                        .setUrl(imgUrl)
                                                                        .setAccessibilityText(
                                                                                "Album cover of an ocean view"))))
                                .setMediaType("AUDIO"));
            } else if (Objects.equals(req.getParameter("artistName"), "Glass Animals")) {
                resp.add("Playing " + req.getParameter("artistName") + " song");
                resp.addSuggestions(new String[]{"Okay", "Cancel", "Next"});
                resp.add(
                        new MediaResponse()
                                .setMediaObjects(
                                        Collections.singletonList(
                                                new MediaObject()
                                                        .setName("Glass Animals")
                                                        .setDescription("Glass Animals")
                                                        .setContentUrl("https://demo7uyidtyietyuiet.s3.ap-south-1.amazonaws.com/Imagine+Dragons+-+Thunder.mp3")
                                                        .setIcon(
                                                                new Image()
                                                                        .setUrl(imgUrl)
                                                                        .setAccessibilityText(
                                                                                "Album cover of an ocean view"))))
                                .setMediaType("AUDIO"));
            }
        }
        return resp.build();
    }

    @Override
    public ActionResponse mediaStatus(ActionRequest req) {
        ResponseBuilder resp = new ResponseBuilder();
        LOGGER.info("play songs : {}", req.getParameter("artistName"));
        if (Boolean.TRUE.equals(globalEntity.getArtistNameFlag())) {
            LOGGER.info("Media Status: {}", req.getMediaStatus());
            String mediaStatus = req.getMediaStatus();
            LOGGER.info("Media Status : {}", mediaStatus);
            if (mediaStatus != null && mediaStatus.equals("FINISHED")) {
                globalEntity.setArtistNameFlag(false);
                resp.add("Song has been completed say next to continue.")
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
    public ActionResponse yesResponse(ActionRequest req) {
        ResponseBuilder resp = new ResponseBuilder();
        ResourceBundle rd = ResourceBundle.getBundle("resources");
        resp.add(rd.getString("yesResponse")).endConversation();
        return resp.build();
    }

    @Override
    public ActionResponse noResponse(ActionRequest req) {
        ResponseBuilder resp = new ResponseBuilder();
        ResourceBundle rd = ResourceBundle.getBundle("resources");
        resp.add(rd.getString("noResponse")).endConversation();
        return resp.build();
    }

    @Override
    public ActionResponse fallBack(ActionRequest req) {

        ResponseBuilder resp = new ResponseBuilder();
        LOGGER.info("Fallback : {}", req.getAppRequest());
        LOGGER.info("Parameter Text: {}", req.getParameter("artistName"));
        String artistName = (String) req.getParameter("artistName");
        ResourceBundle rb = ResourceBundle.getBundle("resources");
        String[] artistNames = {"Arijit sigh", "MArtin Garrix", "Imagine Dragons"};
        Boolean artistNameFlag = globalEntity.getArtistNameFlag();
        if (!Arrays.asList(artistNames).contains(artistName) && Boolean.TRUE.equals(artistNameFlag) && !globalEntity.getPlayTypeFlag()) {
            LOGGER.info("artistNameFlag: {}", artistNameFlag);
            String exampleSsml = rb.getString("notFoundArtist");
            SimpleResponse ssmlResp = new SimpleResponse().setSsml(exampleSsml);
            resp.add(ssmlResp).addSuggestions(artistNames)
                    .add(new LinkOutSuggestion().setDestinationName("Artist Name").setUrl(linkoutSuggestions));
        } else if(globalEntity.getPlayTypeFlag()) {
            resp.add("Not Found Select Below from genere")
                    .addSuggestions(new String[]{"pop", "rock", "romantic", "random"})
                    .add(new LinkOutSuggestion().setDestinationName("Genere").setUrl(linkoutSuggestions));
        } else {
            ResourceBundle rd = ResourceBundle.getBundle("resources");
            resp.add(rd.getString("fallback"));
        }
        return resp.build();
    }
}
