package com.example.musicsystem.requestDto;

public class SessionDto {
    private Integer id;

    private String conversionId;

    private String userName;

    private String requestParam;

    private String intent;

    private String intentRequest; //AppRequest

    private Object globalEntity;

    private Boolean isFallBack;

    public Boolean getIsFallBack() {
        return isFallBack;
    }

    public void setIsFallBack(Boolean isFallBack) {
        this.isFallBack = isFallBack;
    }

    public SessionDto(Integer id, String userName, String requestParam, String intent, String intentRequest, Object globalEntity) {
        this.id = id;
        this.userName = userName;
        this.requestParam = requestParam;
        this.intent = intent;
        this.intentRequest = intentRequest;
        this.globalEntity = globalEntity;
    }

    public SessionDto() {
    }

    public SessionDto(String userName, String requestParam, String intent, String intentRequest, Object globalEntity) {
        this.userName = userName;
        this.requestParam = requestParam;
        this.intent = intent;
        this.intentRequest = intentRequest;
        this.globalEntity = globalEntity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRequestParam() {
        return requestParam;
    }

    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public String getIntentRequest() {
        return intentRequest;
    }

    public void setIntentRequest(String intentRequest) {
        this.intentRequest = intentRequest;
    }

    public Object getGlobalEntity() {
        return globalEntity;
    }

    public void setGlobalEntity(Object globalEntity) {
        this.globalEntity = globalEntity;
    }

    public String getConversionId() {
        return conversionId;
    }

    public void setConversionId(String conversionId) {
        this.conversionId = conversionId;
    }

    public Boolean getFallBack() {
        return isFallBack;
    }

    public void setFallBack(Boolean fallBack) {
        isFallBack = fallBack;
    }

    @Override
    public String toString() {
        return "SessionDto{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", requestParam='" + requestParam + '\'' +
                ", intent='" + intent + '\'' +
                ", intentRequest='" + intentRequest + '\'' +
                ", globalEntity=" + globalEntity +
                ", isFallBack=" + isFallBack +
                '}';
    }


}
