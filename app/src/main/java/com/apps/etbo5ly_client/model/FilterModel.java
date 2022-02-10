package com.apps.etbo5ly_client.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FilterModel implements Serializable {
    private List<String> category_id;
    private String is_delivry;
    private String is_type;
    private String sort_by;
    private String start_work;
    private String end_work;
    private String option_id;
    private String user_id;
    private String latitude;
    private String longitude;

    public FilterModel() {
        category_id = new ArrayList<>();
        is_delivry = "";
        is_type = "all";
        sort_by = "most_rated";
        start_work = "";
        end_work = "";
        option_id = "1";
    }

    public List<String> getCategory_id() {
        return category_id;
    }

    public void setCategory_id(List<String> category_id) {
        this.category_id = category_id;
    }

    public String getIs_delivry() {
        return is_delivry;
    }

    public void setIs_delivry(String is_delivry) {
        this.is_delivry = is_delivry;
    }

    public String getIs_type() {
        return is_type;
    }

    public void setIs_type(String is_type) {
        this.is_type = is_type;
    }

    public String getSort_by() {
        return sort_by;
    }

    public void setSort_by(String sort_by) {
        this.sort_by = sort_by;
    }

    public String getStart_work() {
        return start_work;
    }

    public void setStart_work(String start_work) {
        this.start_work = start_work;
    }

    public String getEnd_work() {
        return end_work;
    }

    public void setEnd_work(String end_work) {
        this.end_work = end_work;
    }

    public String getOption_id() {
        return option_id;
    }

    public void setOption_id(String option_id) {
        this.option_id = option_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
