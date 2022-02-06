package com.apps.etbo5ly_client.model;

import java.io.Serializable;

public class CouponDataModel extends StatusResponse implements Serializable {
    private CouponModel data;

    public CouponModel getData() {
        return data;
    }
}
