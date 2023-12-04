package com.simtechdata;

public class Response {

    private boolean success = false;
    private String message = "";

    public void set(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
