package com.apps.etbo5ly_client.model;

import java.io.Serializable;
import java.util.List;

public class KitchenDataModel extends StatusResponse implements Serializable {
    private List<KitchenModel> data;

    public List<KitchenModel> getData() {
        return data;
    }
}
