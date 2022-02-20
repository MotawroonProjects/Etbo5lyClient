package com.apps.etbo5ly_client.model;

import java.io.Serializable;

public class SingleAddress extends StatusResponse implements Serializable {
    private AddressModel data;

    public AddressModel getData() {
        return data;
    }
}
