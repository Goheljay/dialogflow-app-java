package com.example.musicsystem.service;

import com.example.musicsystem.entity.GlobalEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;

public interface MusicService {
    ActionResponse welcome(ActionRequest req);

    ActionResponse fallBack(ActionRequest req);

    ActionResponse askName(ActionRequest req, GlobalEntity entity);

    ActionResponse playSong(ActionRequest req);

    ActionResponse noResponse(ActionRequest req);

    ActionResponse mediaStatus(ActionRequest req);

    ActionResponse mediaStatusNext(ActionRequest req);

    ActionResponse typeToPlay(ActionRequest req, GlobalEntity entity);

    ActionResponse noReviewResponse(ActionRequest req);

    ActionResponse yesReviewResponse(ActionRequest req);

}
