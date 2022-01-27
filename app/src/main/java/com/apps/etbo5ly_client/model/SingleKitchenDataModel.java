package com.apps.etbo5ly_client.model;

import java.io.Serializable;

public class SingleKitchenDataModel extends StatusResponse implements Serializable {
    private KitchenModel data;

    public KitchenModel getData() {
        return data;
    }
}
