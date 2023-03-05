package com.melomanya.groupchatapp.model.response;

public class SuccessResponse implements BaseResponse{

    private String message;

    public SuccessResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}