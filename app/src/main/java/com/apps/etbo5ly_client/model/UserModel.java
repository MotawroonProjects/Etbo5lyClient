package com.apps.etbo5ly_client.model;

import java.io.Serializable;

public class UserModel extends StatusResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public static class Data implements Serializable {

        private String id;
        private String name;
        private String phone;
        private String phone_code;
        private String email;
        private String photo;
        private String yes_i_read_it;
        private String longitude;
        private String latitude;
        private String type;


        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getPhone() {
            return phone;
        }

        public String getPhone_code() {
            return phone_code;
        }

        public String getEmail() {
            return email;
        }

        public String getPhoto() {
            return photo;
        }

        public String getYes_i_read_it() {
            return yes_i_read_it;
        }

        public String getLongitude() {
            return longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public String getType() {
            return type;
        }

        private static String firebase_token;

        public String getFirebase_token() {
            return firebase_token;
        }


        public void setFirebase_token(String firebase_token) {
            this.firebase_token = firebase_token;
        }
    }

}
