package com.apps.etbo5ly_client.model;

import java.io.Serializable;

public class CountryModel implements Serializable {

    private String id;
    private String governorates_id;
    private String titel;

    public String getId() {
        return id;
    }

    public String getGovernorates_id() {
        return governorates_id;
    }

    public String getTitel() {
        return titel;
    }

}
