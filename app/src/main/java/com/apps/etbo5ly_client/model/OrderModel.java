package com.apps.etbo5ly_client.model;

import java.io.Serializable;
import java.util.List;

public class OrderModel implements Serializable {
    private String id;
    private String user_id;
    private String caterer_id;
    private String option_id;
    private String total;
    private String zone_id;
    private String notes;
    private String address;
    private String booking_date;
    private String status_order;
    private String is_end;
    private String cancel_by;
    private Object why_cancel;
    private String is_pay;
    private String is_rate;
    private String paid_type;
    private String created_at;
    private String updated_at;
    private UserModel.Data user;
    private ZoneCover.Zone zones;
    private KitchenModel caterer;
    private List<OrderDetail> order_details;

    public String getId() {
        return id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getCaterer_id() {
        return caterer_id;
    }

    public String getOption_id() {
        return option_id;
    }

    public String getTotal() {
        return total;
    }

    public String getZone_id() {
        return zone_id;
    }

    public String getNotes() {
        return notes;
    }

    public String getAddress() {
        return address;
    }

    public String getBooking_date() {
        return booking_date;
    }

    public String getStatus_order() {
        return status_order;
    }

    public String getIs_end() {
        return is_end;
    }

    public String getCancel_by() {
        return cancel_by;
    }

    public Object getWhy_cancel() {
        return why_cancel;
    }

    public String getIs_pay() {
        return is_pay;
    }

    public String getPaid_type() {
        return paid_type;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public UserModel.Data getUser() {
        return user;
    }

    public ZoneCover.Zone getZones() {
        return zones;
    }

    public KitchenModel getCaterer() {
        return caterer;
    }

    public List<OrderDetail> getOrder_details() {
        return order_details;
    }

    public String getIs_rate() {
        return is_rate;
    }

    public void setIs_rate(String is_rate) {
        this.is_rate = is_rate;
    }

    public static class OrderDetail implements Serializable {
        private String id;
        private String order_id;
        private String offer_id;
        private String qty;
        private String dishes_id;
        private String buffets_id;
        private String feast_id;
        private String created_at;
        private String updated_at;
        private OfferModel offer;
        private DishModel dishes;
        private BuffetModel buffet;
        private BuffetModel feast;

        public String getId() {
            return id;
        }

        public String getOrder_id() {
            return order_id;
        }

        public String getOffer_id() {
            return offer_id;
        }

        public String getQty() {
            return qty;
        }

        public String getDishes_id() {
            return dishes_id;
        }

        public String getBuffets_id() {
            return buffets_id;
        }

        public String getFeast_id() {
            return feast_id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public OfferModel getOffer() {
            return offer;
        }

        public DishModel getDishes() {
            return dishes;
        }

        public BuffetModel getBuffet() {
            return buffet;
        }

        public BuffetModel getFeast() {
            return feast;
        }
    }
}
