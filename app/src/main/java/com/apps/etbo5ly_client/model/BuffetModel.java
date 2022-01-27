package com.apps.etbo5ly_client.model;

import java.io.Serializable;
import java.util.List;

public class BuffetModel implements Serializable {
    private String id;
    private String titel;
    private String photo;
    private String number_people;
    private String order_time;
    private String service_provider_type;
    private String price;
    private String category_dishes_id;
    private String is_completed;
    private String caterer_id;
    private String created_at;
    private String updated_at;
    private List<Category> categor_dishes;

    public String getId() {
        return id;
    }

    public String getTitel() {
        return titel;
    }

    public String getPhoto() {
        return photo;
    }

    public String getNumber_people() {
        return number_people;
    }

    public String getOrder_time() {
        return order_time;
    }

    public String getService_provider_type() {
        return service_provider_type;
    }

    public String getPrice() {
        return price;
    }

    public String getCategory_dishes_id() {
        return category_dishes_id;
    }

    public String getCaterer_id() {
        return caterer_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getIs_completed() {
        return is_completed;
    }

    public List<Category> getCategor_dishes() {
        return categor_dishes;
    }

    public static class Category implements Serializable {
        private String id;
        private String titel;
        private String caterer_id;
        private List<DishModel> dishes_buffet;

        public String getId() {
            return id;
        }

        public String getTitel() {
            return titel;
        }

        public String getCaterer_id() {
            return caterer_id;
        }

        public List<DishModel> getDishes_buffet() {
            return dishes_buffet;
        }
    }
}
