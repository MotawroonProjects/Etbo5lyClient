package com.apps.etbo5ly_client.model;

import java.io.Serializable;
import java.util.List;

public class BuffetsDataModel extends StatusResponse implements Serializable {
    private List<BuffetModel> data;

    public List<BuffetModel> getData() {
        return data;
    }
}
