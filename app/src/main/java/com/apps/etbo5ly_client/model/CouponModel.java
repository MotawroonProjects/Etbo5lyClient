package com.apps.etbo5ly_client.model;

import java.io.Serializable;

public class CouponModel implements Serializable {
    private String id;
    private String name;
    private String amount;
    private String is_active;
    private String created_at;
    private String updated_at;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAmount() {
        return amount;
    }

    public String getIs_active() {
        return is_active;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
