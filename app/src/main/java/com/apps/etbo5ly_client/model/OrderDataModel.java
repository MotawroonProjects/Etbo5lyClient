package com.apps.etbo5ly_client.model;

import java.io.Serializable;
import java.util.List;

public class OrderDataModel extends StatusResponse implements Serializable {
    private List<OrderModel> myOrder;

    public List<OrderModel> getMyOrder() {
        return myOrder;
    }
}
