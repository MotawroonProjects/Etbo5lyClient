package com.apps.etbo5ly_client.model;

import java.io.Serializable;

public class DishModel implements Serializable {
    private String id;
    private String category_dishes_id;
    private String caterer_id;
    private String buffets_id;
    private String feast_id;
    private String titel;
    private String photo;
    private String price;
    private String details;
    private String qty;
    private String created_at;
    private String updated_at;

    public String getId() {
        return id;
    }

    public String getCategory_dishes_id() {
        return category_dishes_id;
    }

    public String getCaterer_id() {
        return caterer_id;
    }

    public String getBuffets_id() {
        return buffets_id;
    }

    public String getFeast_id() {
        return feast_id;
    }

    public String getTitel() {
        return titel;
    }

    public String getPhoto() {
        return photo;
    }

    public String getPrice() {
        return price;
    }

    public String getDetails() {
        return details;
    }

    public String getQty() {
        return qty;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
