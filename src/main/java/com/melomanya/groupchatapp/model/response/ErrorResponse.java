package com.melomanya.groupchatapp.model.response;

import java.lang.reflect.Field;

public class ErrorResponse implements BaseResponse{

    private Object[] fields;

    private ErrorType type;

    private boolean isRelatedByServer;

    public ErrorResponse(Object[] fields, ErrorType type, boolean isRelatedByServer) {
        this.fields = fields;
        this.type = type;
        this.isRelatedByServer = isRelatedByServer;
    }

    public Object[] getFields() {
        return fields;
    }

    public void setFields(Object[] fields) {
        this.fields = fields;
    }

    public ErrorType getType() {
        return type;
    }

    public void setType(ErrorType type) {
        this.type = type;
    }

}