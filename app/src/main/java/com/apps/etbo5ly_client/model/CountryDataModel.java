package com.apps.etbo5ly_client.model;

import java.io.Serializable;
import java.util.List;

public class CountryDataModel extends StatusResponse implements Serializable {
    private List<CountryModel> data;

    public List<CountryModel> getData() {
        return data;
    }
}
