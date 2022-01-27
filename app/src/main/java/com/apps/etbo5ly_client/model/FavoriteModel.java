package com.apps.etbo5ly_client.model;

import java.io.Serializable;

public class FavoriteModel implements Serializable {
    private String id;
    private String user_id;
    private String caterer_id;
    private String created_at;
    private String updated_at;
    private UserModel.Data user;

    public String getId() {
        return id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getCaterer_id() {
        return caterer_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public UserModel.Data getUser() {
        return user;
    }
}
