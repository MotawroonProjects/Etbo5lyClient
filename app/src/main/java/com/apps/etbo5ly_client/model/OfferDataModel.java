package com.apps.etbo5ly_client.model;

import java.io.Serializable;
import java.util.List;

public class OfferDataModel extends StatusResponse implements Serializable {
    private List<OfferModel> data;

    public List<OfferModel> getData() {
        return data;
    }
}
