package com.apps.etbo5ly_client.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CartOrderModel implements Serializable {
    private String user_id = "";
    private String option_id = "";
    private String caterer_id = "";
    private String total = "0.0";
    private String notes = "";
    private String booking_date = "";
    private String zone_id = "";
    private String address = "";
    private String copon = "";
    private String paid_type="cash";
    private List<SendOrderModel.Details> details = new ArrayList<>();

    public CartOrderModel(String user_id, String option_id, String caterer_id, String total, String notes, String booking_date, String zone_id, String address, String copon, String paid_type, List<SendOrderModel.Details> details) {
        this.user_id = user_id;
        this.option_id = option_id;
        this.caterer_id = caterer_id;
        this.total = total;
        this.notes = notes;
        this.booking_date = booking_date;
        this.zone_id = zone_id;
        this.address = address;
        this.copon = copon;
        this.paid_type = paid_type;
        this.details = details;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getOption_id() {
        return option_id;
    }

    public void setOption_id(String option_id) {
        this.option_id = option_id;
    }

    public String getCaterer_id() {
        return caterer_id;
    }

    public void setCaterer_id(String caterer_id) {
        this.caterer_id = caterer_id;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(String booking_date) {
        this.booking_date = booking_date;
    }

    public String getZone_id() {
        return zone_id;
    }

    public void setZone_id(String zone_id) {
        this.zone_id = zone_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCopon() {
        return copon;
    }

    public void setCopon(String copon) {
        this.copon = copon;
    }

    public String getPaid_type() {
        return paid_type;
    }

    public void setPaid_type(String paid_type) {
        this.paid_type = paid_type;
    }

    public List<SendOrderModel.Details> getDetails() {
        return details;
    }

    public void setDetails(List<SendOrderModel.Details> details) {
        this.details = details;
    }
}
