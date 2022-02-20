package com.apps.etbo5ly_client.model;

import java.io.Serializable;

public class AddressModel implements Serializable {
    private String id;
    private String user_id;
    private String address;
    private String zone_id;
    private String type;
    private String created_at;
    private String updated_at;
    private ZoneCover zone_cover;

    public String getId() {
        return id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getAddress() {
        return address;
    }

    public String getZone_id() {
        return zone_id;
    }

    public String getType() {
        return type;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public ZoneCover getZone_cover() {
        return zone_cover;
    }
}
