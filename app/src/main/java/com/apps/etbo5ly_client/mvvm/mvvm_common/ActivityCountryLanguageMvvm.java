package com.apps.etbo5ly_client.mvvm.mvvm_common;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.apps.etbo5ly_client.model.CountryModel;
import com.apps.etbo5ly_client.model.SelectedLocation;

import io.reactivex.disposables.CompositeDisposable;

public class ActivityCountryLanguageMvvm extends AndroidViewModel {
    private static final String TAG = "ActivityCountrylangMvvm";
    private Context context;

    private MutableLiveData<CountryModel> selectedCountryLivData;
    private MutableLiveData<CountryModel> selectedCityLivData;
    private MutableLiveData<SelectedLocation> selectedLocationData;

    private CompositeDisposable disposable = new CompositeDisposable();


    public ActivityCountryLanguageMvvm(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public MutableLiveData<CountryModel> getSelectedCountry() {
        if (selectedCountryLivData == null) {
            selectedCountryLivData = new MutableLiveData<>();
        }
        return selectedCountryLivData;
    }

    public MutableLiveData<CountryModel> getSelectedCity() {
        if (selectedCityLivData == null) {
            selectedCityLivData = new MutableLiveData<>();
        }
        return selectedCityLivData;
    }

    public MutableLiveData<SelectedLocation> getSelectedLocation() {
        if (selectedLocationData == null) {
            selectedLocationData = new MutableLiveData<>();
        }
        return selectedLocationData;
    }

    public void setSelectedCountry(CountryModel countryModel) {
        getSelectedCountry().setValue(countryModel);
    }

    public void setSelectedCity(CountryModel countryModel) {
        getSelectedCity().setValue(countryModel);
    }

    public void setSelectedLocation(SelectedLocation selectedLocation) {
        getSelectedLocation().setValue(selectedLocation);
    }






    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();

    }

}
