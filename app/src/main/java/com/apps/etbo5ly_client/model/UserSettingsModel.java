package com.apps.etbo5ly_client.model;

import java.io.Serializable;

public class UserSettingsModel implements Serializable {
    private boolean isFirstTime = true;
    private CountryModel countryModel;
    private CountryModel cityModel;
    private SelectedLocation location;
    private String option_id = "";

    public boolean isFirstTime() {
        return isFirstTime;
    }

    public void setFirstTime(boolean isFirstTime) {
        this.isFirstTime = isFirstTime;
    }

    public void setCountryModel(CountryModel countryModel) {
        this.countryModel = countryModel;
    }

    public CountryModel getCountryModel() {
        return countryModel;
    }

    public CountryModel getCityModel() {
        return cityModel;
    }

    public void setCityModel(CountryModel cityModel) {
        this.cityModel = cityModel;
    }

    public SelectedLocation getLocation() {
        return location;
    }

    public void setLocation(SelectedLocation location) {
        this.location = location;
    }

    public String getOption_id() {
        return option_id;
    }

    public void setOption_id(String option_id) {
        this.option_id = option_id;
    }
}
