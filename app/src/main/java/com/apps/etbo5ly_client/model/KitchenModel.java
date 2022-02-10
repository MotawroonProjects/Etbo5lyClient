package com.apps.etbo5ly_client.model;

import java.io.Serializable;
import java.util.List;

public class KitchenModel implements Serializable {
    private String id;
    private String category_id;
    private String user_id;
    private String option_id;
    private String notes;
    private String sex_type;
    private String is_delivry;
    private String delivry_time;
    private String processing_time;
    private String Number_of_reservation_days;
    private String is_Special;
    private String free_delivery;
    private String longitude;
    private String latitude;
    private String address;
    private String start_work;
    private String end_work;
    private String tax = "0.0";
    private String customers_service = "0.0";
    private String delivry_cost = "0.0";
    private String discount = "0.0";
    private String commercial_register;
    private String is_completed;
    private String is_favorite = "false";
    private String rates_val;
    private String rates_count;
    private List<ZoneCover> zone_cover;
    private UserModel.Data user;
    private List<Photo> photos;
    private List<BuffetModel> buffets;
    private List<FeastModel> feasts;
    private List<RateModel> rate;
    private List<FavoriteModel> favorite;
    private List<OfferModel> offers;


    public String getId() {
        return id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getOption_id() {
        return option_id;
    }

    public String getNotes() {
        return notes;
    }

    public String getSex_type() {
        return sex_type;
    }

    public String getIs_delivry() {
        return is_delivry;
    }

    public String getDelivry_time() {
        return delivry_time;
    }

    public String getProcessing_time() {
        return processing_time;
    }

    public String getNumber_of_reservation_days() {
        return Number_of_reservation_days;
    }

    public String getIs_Special() {
        return is_Special;
    }

    public String getFree_delivery() {
        return free_delivery;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getAddress() {
        return address;
    }

    public String getStart_work() {
        return start_work;
    }

    public String getEnd_work() {
        return end_work;
    }

    public String getTax() {
        return tax;
    }

    public String getCustomers_service() {
        return customers_service;
    }

    public String getDelivry_cost() {
        return delivry_cost;
    }

    public String getDiscount() {
        return discount;
    }

    public String getCommercial_register() {
        return commercial_register;
    }

    public String getIs_completed() {
        return is_completed;
    }

    public String getIs_favorite() {
        return is_favorite;
    }

    public void setIs_favorite(String is_favorite) {
        this.is_favorite = is_favorite;
    }

    public String getRates_val() {
        return rates_val;
    }

    public String getRates_count() {
        return rates_count;
    }

    public UserModel.Data getUser() {
        return user;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public List<BuffetModel> getBuffets() {
        return buffets;
    }

    public List<FeastModel> getFeasts() {
        return feasts;
    }

    public List<RateModel> getRate() {
        return rate;
    }

    public List<FavoriteModel> getFavorite() {
        return favorite;
    }

    public List<OfferModel> getOffers() {
        return offers;
    }

    public List<ZoneCover> getZone_cover() {
        return zone_cover;
    }

    public static class Photo implements Serializable {
        private String id;
        private String caterers_id;
        private String photo;
        private String created_at;
        private String updated_at;

        public String getId() {
            return id;
        }

        public String getCaterers_id() {
            return caterers_id;
        }

        public String getPhoto() {
            return photo;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }
    }
}
