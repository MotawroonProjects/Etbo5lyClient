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
    private int amount = 0;

    public DishModel(String id, String category_dishes_id, String caterer_id, String buffets_id, String feast_id, String titel, String photo, String price, String details, String qty, String created_at, String updated_at, int amount) {
        this.id = id;
        this.category_dishes_id = category_dishes_id;
        this.caterer_id = caterer_id;
        this.buffets_id = buffets_id;
        this.feast_id = feast_id;
        this.titel = titel;
        this.photo = photo;
        this.price = price;
        this.details = details;
        this.qty = qty;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.amount = amount;
    }

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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
