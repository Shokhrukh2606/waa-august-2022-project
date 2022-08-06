package com.example.backend.exceptions;

public enum ErrorCode {

    ENTITY_NOT_FOUND,
    COULD_NOT_CREATE_ENTITY,

    MISSING_REQUIRED_FIELD,
    MISSING_REQUIRED_FILE,

    NOT_A_STRING,
    NOT_A_LATIN,
    NOT_A_NUMBER,
    NOT_A_BOOLEAN,
    NOT_A_PHONE,
    NOT_A_DATE_TIME,
    NOT_A_DATE,
    NOT_A_LIST,
    FORBIDDEN,
    COULD_NOT_INITIALIZE_DIRECTION,
    FILE_EMPTY,
    FILED_STORE_EMPTY_FILE,
    USERNAME_NOT_FOUND
    ;

}
