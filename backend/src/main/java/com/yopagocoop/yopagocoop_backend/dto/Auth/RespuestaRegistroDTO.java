package com.yopagocoop.yopagocoop_backend.dto.Auth;

public class RespuestaRegistroDTO {
    private boolean status;
    private String message;

    public RespuestaRegistroDTO(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}