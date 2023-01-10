package com.example.musicsystem.responseDto;

public class ApiResponse {
    private Object data;

    private String massage;

    private String code;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "data=" + data +
                ", massage='" + massage + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
