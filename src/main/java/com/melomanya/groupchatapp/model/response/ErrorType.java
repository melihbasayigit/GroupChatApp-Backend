package com.melomanya.groupchatapp.model.response;

public enum ErrorType {
    WRONG_ENUM_TYPE(-1),
    NULL_VARIABLE(0),
    EMPTY_STRING_VARIABLE(1),
    EMPTY_LIST_VARIABLE(2);

    ErrorType(int code) {
    }
}