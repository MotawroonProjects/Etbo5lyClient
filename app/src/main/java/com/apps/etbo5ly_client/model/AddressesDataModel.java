package com.apps.etbo5ly_client.model;

import java.io.Serializable;
import java.util.List;

public class AddressesDataModel extends StatusResponse implements Serializable {
    private List<AddressModel> data;

    public List<AddressModel> getData() {
        return data;
    }
}
