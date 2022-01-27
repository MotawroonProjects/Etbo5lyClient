package com.apps.etbo5ly_client.model;

import java.io.Serializable;
import java.util.List;

public class FeastModel implements Serializable {
    private String id;
    private String titel;
    private String photo;
    private String number_people;
    private String order_time;
    private String service_provider_type;
    private String price;
    private String category_dishes_id;
    private String caterer_id;
    private String created_at;
    private String updated_at;
    private CategoryDishes categor_dishes;


    public static class CategoryDishes implements Serializable {
        private String id;
        private String titel;
        private String caterer_id;
        private String created_at;
        private String updated_at;
        private List<DishModel> dishes;

        public String getId() {
            return id;
        }

        public String getTitel() {
            return titel;
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

        public List<DishModel> getDishes() {
            return dishes;
        }
    }
}
