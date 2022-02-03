package com.apps.etbo5ly_client.model;

import com.google.gson.annotations.SerializedName;

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
    private boolean isInCart = false;
    private int amountInCart = 0;
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

    public int getAmountInCart() {
        return amountInCart;
    }

    public void setAmountInCart(int amountInCart) {
        this.amountInCart = amountInCart;
    }

    public List<Category> getCategor_dishes() {
        return categor_dishes;
    }

    public boolean isInCart() {
        return isInCart;
    }

    public void setInCart(boolean inCart) {
        isInCart = inCart;
    }

    public static class Category implements Serializable {
        private String id;
        private String titel;
        private String caterer_id;
        private boolean isSelected = false;
        @SerializedName(value = "dishes_buffet",alternate = {"dishes","dishes_feast"})
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

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public void setDishes_buffet(List<DishModel> dishes_buffet) {
            this.dishes_buffet = dishes_buffet;
        }
    }
}
