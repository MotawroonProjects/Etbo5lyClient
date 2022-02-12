package com.apps.etbo5ly_client.mvvm.mvvm_catering;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.apps.etbo5ly_client.model.CountryModel;
import com.apps.etbo5ly_client.model.SelectedLocation;

import io.reactivex.disposables.CompositeDisposable;

public class FragmentLanguageMvvm extends AndroidViewModel {
    private static final String TAG = "FragmentCountrylangMvvm";

    private MutableLiveData<CountryModel> selectedCountryLivData;
    private MutableLiveData<CountryModel> selectedCityLivData;
    private MutableLiveData<SelectedLocation> selectedLocationData;

    private CompositeDisposable disposable = new CompositeDisposable();


    public FragmentLanguageMvvm(@NonNull Application application) {
        super(application);
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






    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();

    }

}
