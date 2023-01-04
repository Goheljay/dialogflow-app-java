package com.example.musicsystem.entity;

import org.apache.http.HttpStatus;

public class ApiResponse {
    private Object data;

    private String massage;

    private HttpStatus code;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public HttpStatus getCode() {
        return code;
    }

    public void setCode(HttpStatus code) {
        this.code = code;
    }
}
