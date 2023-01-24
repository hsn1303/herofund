package com.fpt.edu.herofundbackend.exception;

public class MyBadRequestException extends RuntimeException{

    public MyBadRequestException(String m) {
        super(m);
    }
}
