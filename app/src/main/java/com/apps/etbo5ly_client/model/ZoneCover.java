package com.apps.etbo5ly_client.model;

import java.io.Serializable;

public class ZoneCover implements Serializable {
    private String id;
    private String caterer_id;
    private String zone_id;
    private String zone_cost;
    private String created_at;
    private String updated_at;
    private Zone zone;

    public String getId() {
        return id;
    }

    public String getCaterer_id() {
        return caterer_id;
    }

    public String getZone_id() {
        return zone_id;
    }

    public String getZone_cost() {
        return zone_cost;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public Zone getZone() {
        return zone;
    }

    public static class Zone implements Serializable {
        private String id;
        private String titel;
        private String citie_id;
        private String created_at;
        private String updated_at;

        public String getId() {
            return id;
        }

        public String getTitel() {
            return titel;
        }

        public String getCitie_id() {
            return citie_id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }
    }
}
