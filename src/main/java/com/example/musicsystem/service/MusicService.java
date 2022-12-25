package com.example.musicsystem.service;

import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;

public interface MusicService {
    ActionResponse welcome(ActionRequest req);

    ActionResponse fallBack(ActionRequest req);

    ActionResponse askName(ActionRequest req);

    ActionResponse playSong(ActionRequest req);

    ActionResponse yesResponse(ActionRequest req);

    ActionResponse noResponse(ActionRequest req);

    ActionResponse mediaStatus(ActionRequest req);

    ActionResponse mediaStatusNext(ActionRequest req);

    ActionResponse typeToPlay(ActionRequest req);
}