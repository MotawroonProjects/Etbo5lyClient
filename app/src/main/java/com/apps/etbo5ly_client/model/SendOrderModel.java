package com.apps.etbo5ly_client.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SendOrderModel implements Serializable {
    private String user_id = "";
    private String option_id = "";
    private String caterer_id = "";
    private String total = "";
    private String address_id = "";
    private String notes = "";
    private String booking_date = "";
    private String copon = "";
    private List<Details> details = new ArrayList<>();

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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
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

    public String getCopon() {
        return copon;
    }

    public void setCopon(String copon) {
        this.copon = copon;
    }

    public List<Details> getDetails() {
        return details;
    }

    public void setDetails(List<Details> details) {
        this.details = details;
    }

    public static class Details implements Serializable {
        private String offer_id;
        private String dishes_id;
        private String buffets_id;
        private String feast_id;
        private String caterer_id;
        private String qty;
        private String image;
        private String name;
        private String price;

        public Details(String offer_id, String dishes_id, String buffets_id, String feast_id,String caterer_id, String qty, String image, String name, String price) {

            this.offer_id = offer_id;
            this.dishes_id = dishes_id;
            this.buffets_id = buffets_id;
            this.feast_id = feast_id;
            this.caterer_id = caterer_id;
            this.qty = qty;
            this.image = image;
            this.name = name;
            this.price = price;

        }


        public String getOffer_id() {
            return offer_id;
        }

        public void setOffer_id(String offer_id) {
            this.offer_id = offer_id;
        }

        public String getDishes_id() {
            return dishes_id;
        }

        public void setDishes_id(String dishes_id) {
            this.dishes_id = dishes_id;
        }

        public String getBuffets_id() {
            return buffets_id;
        }

        public void setBuffets_id(String buffets_id) {
            this.buffets_id = buffets_id;
        }

        public String getFeast_id() {
            return feast_id;
        }

        public void setFeast_id(String feast_id) {
            this.feast_id = feast_id;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getCaterer_id() {
            return caterer_id;
        }

        public void setCaterer_id(String caterer_id) {
            this.caterer_id = caterer_id;
        }
    }

}
