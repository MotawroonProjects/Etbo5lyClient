package com.apps.etbo5ly_client.model;

import java.io.Serializable;

public class CategoryModel implements Serializable {
    private String id;
    private String titel;
    private String photo;
    private String created_at;
    private String updated_at;
    private boolean isSelected = false;

    public String getId() {
        return id;
    }

    public String getTitel() {
        return titel;
    }

    public String getPhoto() {
        return photo;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
