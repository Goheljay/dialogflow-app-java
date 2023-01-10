package com.example.musicsystem.requestDto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConversationDto {
    @JsonProperty
    private String conversationId;
    @JsonProperty
    private String type;

    public ConversationDto(String conversationId, String type) {
        this.conversationId = conversationId;
        this.type = type;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ConversationDto{" +
                "conversationId='" + conversationId + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public ConversationDto() {
    }
}
