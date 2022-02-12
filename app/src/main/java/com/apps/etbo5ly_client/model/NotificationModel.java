package com.apps.etbo5ly_client.model;

import java.io.Serializable;

public class NotificationModel implements Serializable {
    private String id;
    private String user_id;
    private String user_name;
    private String caterer_id;
    private String order_id;
    private String caterer_name;
    private String title;
    private String body;
    private String body_caterer;
    private String status;
    private String created_at;
    private String updated_at;

    public String getId() {
        return id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getCaterer_id() {
        return caterer_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getCaterer_name() {
        return caterer_name;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getBody_caterer() {
        return body_caterer;
    }

    public String getStatus() {
        return status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
