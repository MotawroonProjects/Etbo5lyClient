package com.apps.etbo5ly_client.model;

import java.io.Serializable;
import java.util.List;

public class DishesDataModel extends StatusResponse implements Serializable {
    private List<BuffetModel.Category> data;

    public List<BuffetModel.Category> getData() {
        return data;
    }
}
