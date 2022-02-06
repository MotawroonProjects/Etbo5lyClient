package com.apps.etbo5ly_client.model;

import android.content.Context;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.apps.etbo5ly_client.BR;
import com.apps.etbo5ly_client.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SendOrderModel extends BaseObservable implements Serializable {
    private String user_id = "";
    private String option_id = "";
    private String caterer_id = "";
    private String total = "0.0";
    private String address_id = "";
    private String notes = "";
    private String booking_date = "";
    private String zone_id = "";
    private String zone = "";
    private String address = "";
    private String copon = "";
    private String coupon_value = "0.0";
    private boolean hasZone = false;
    private boolean isValid = false;
    private List<Details> details = new ArrayList<>();
    public ObservableField<String> error_zone = new ObservableField<>();
    public ObservableField<String> error_address = new ObservableField<>();
    public ObservableField<String> error_date = new ObservableField<>();
    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public boolean isDataValid() {
        if (!address.isEmpty() &&
                !booking_date.isEmpty()) {
            if (hasZone) {
                if (zone_id.isEmpty()) {
                    error_zone.set(context.getString(R.string.field_required));
                    isValid = false;
                    return false;
                }
            }
            error_date.set(null);
            error_address.set(null);
            error_zone.set(null);
            isValid = true;
            return true;
        } else {
            if (!zone_id.isEmpty()) {
                error_zone.set(null);

            } else {
                error_zone.set(context.getString(R.string.field_required));

            }

            if (!address.isEmpty()) {
                error_address.set(null);

            } else {
                error_address.set(context.getString(R.string.field_required));

            }

            if (!booking_date.isEmpty()) {
                error_date.set(null);

            } else {
                error_date.set(context.getString(R.string.field_required));

            }

            if (hasZone) {
                if (zone_id.isEmpty()) {
                    error_zone.set(context.getString(R.string.field_required));

                }
            }

        }
        isValid = false;
        return false;
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

    public boolean isHasZone() {
        return hasZone;
    }

    public void setHasZone(boolean hasZone) {
        this.hasZone = hasZone;
    }

    public boolean isValid() {
        return isValid;
    }

    @Bindable
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
        notifyPropertyChanged(BR.notes);
    }

    @Bindable
    public String getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(String booking_date) {
        this.booking_date = booking_date;
        notifyPropertyChanged(BR.booking_date);
        isDataValid();

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

    public String getZone_id() {
        return zone_id;
    }

    public void setZone_id(String zone_id) {
        this.zone_id = zone_id;
        isDataValid();
    }

    @Bindable
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        notifyPropertyChanged(com.apps.etbo5ly_client.BR.address);
        isDataValid();

    }

    @Bindable
    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
        notifyPropertyChanged(BR.zone);
    }

    @Bindable
    public String getCoupon_value() {
        return coupon_value;
    }

    public void setCoupon_value(String coupon_value) {
        this.coupon_value = coupon_value;
        notifyPropertyChanged(BR.coupon_value);
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
        private String item_type;

        public Details(String offer_id, String dishes_id, String buffets_id, String feast_id, String caterer_id, String qty, String image, String name, String price, String item_type) {

            this.offer_id = offer_id;
            this.dishes_id = dishes_id;
            this.buffets_id = buffets_id;
            this.feast_id = feast_id;
            this.caterer_id = caterer_id;
            this.qty = qty;
            this.image = image;
            this.name = name;
            this.price = price;
            this.item_type = item_type;

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

        public String getItem_type() {
            return item_type;
        }

        public void setItem_type(String item_type) {
            this.item_type = item_type;
        }
    }

}
