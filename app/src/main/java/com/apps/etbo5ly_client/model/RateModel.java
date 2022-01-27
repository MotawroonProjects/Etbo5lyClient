package com.apps.etbo5ly_client.model;

import java.io.Serializable;

public class RateModel implements Serializable {
    private String id;
    private String user_id;
    private String caterer_id;
    private String value;
    private String comment;
    private String created_at;
    private String updated_at;

    public String getId() {
        return id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getCaterer_id() {
        return caterer_id;
    }

    public String getValue() {
        return value;
    }

    public String getComment() {
        return comment;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
