package com.apps.etbo5ly_client.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SingleOrderDataModel extends StatusResponse implements Serializable {
    @SerializedName(value = "SingelOrder", alternate = {"data"})
    private OrderModel SingelOrder;

    public OrderModel getSingelOrder() {
        return SingelOrder;
    }
}
