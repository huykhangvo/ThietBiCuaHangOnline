package com.boyhotkey96.thietbicuahangonline.model;

import com.boyhotkey96.thietbicuahangonline.model.User;

public class ServerRequest {

    private String operation;
    private User user;

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
