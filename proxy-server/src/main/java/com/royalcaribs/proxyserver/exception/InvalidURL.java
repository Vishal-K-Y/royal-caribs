package com.royalcaribs.proxyserver.exception;

public class InvalidURL extends RuntimeException{
    public InvalidURL(String url){
        super(url+" doesn't exist");
    }
}