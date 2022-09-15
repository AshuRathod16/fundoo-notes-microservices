package com.bridgelabz.fundoonotes.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

/*
 * Purpose : AdminNotFoundException is used to handle the exceptions
 * Version : 1.0
 * @author : Ashwini Rathod
 * */
@ResponseStatus
public class UserException extends RuntimeException {
    private int statusCode;
    private String statusMessage;

    public UserException(int statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }
}
