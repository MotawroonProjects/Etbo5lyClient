package com.apps.etbo5ly_client.uis;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.model.SelectedLocation;
import com.apps.etbo5ly_client.model.UserSettingsModel;
import com.apps.etbo5ly_client.mvvm.mvvm_catering.ActivityHomeGeneralMvvm;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseFragment;
import com.apps.etbo5ly_client.uis.common_uis.activity_map.MapActivity;

import java.util.HashSet;
import java.util.Set;

public class FragmentBaseNavigation extends BaseFragment {
    private final int defaultVal = -1;
    private int layoutResource = -1;
    private int toolBarId = -1;
    private int navHostId = -1;
    private Set<Integer> rootDestination = new HashSet<>();
    private AppBarConfiguration appBarConfiguration;
    private NavController navController;
    private AppCompatActivity activity;
    private View root;
    private ActivityHomeGeneralMvvm activityHomeGeneralMvvm;
    private int req;
    private ActivityResultLauncher<Intent> launcher;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (AppCompatActivity) context;
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (req == 1 && result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                SelectedLocation location = (SelectedLocation) result.getData().getSerializableExtra("location");
                UserSettingsModel userSettingsModel = getUserSettings();
                userSettingsModel.setLocation(location);
                setUserSettings(userSettingsModel);
                activityHomeGeneralMvvm.updateLocation(userSettingsModel);
            }
        });
    }

    public static FragmentBaseNavigation newInstance(int layoutResource, int toolBarId, int navHostId) {
        FragmentBaseNavigation instance = new FragmentBaseNavigation();
        Bundle bundle = new Bundle();
        bundle.putInt("layoutResource", layoutResource);
        bundle.putInt("toolBarId", toolBarId);
        bundle.putInt("navHostId", navHostId);
        instance.setArguments(bundle);
        return instance;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            layoutResource = bundle.getInt("layoutResource");
            toolBarId = bundle.getInt("toolBarId");
            navHostId = bundle.getInt("navHostId");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (layoutResource == defaultVal || toolBarId == defaultVal || navHostId == defaultVal) {
            return null;
        } else {
            root = inflater.inflate(layoutResource, container, false);
            return root;
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (layoutResource == defaultVal || toolBarId == defaultVal || navHostId == defaultVal) {
            return;
        }

        TextView tvTitle = root.findViewById(R.id.tvTitle);
        LinearLayout llLocation = root.findViewById(R.id.llLocation);

        activityHomeGeneralMvvm = ViewModelProviders.of(activity).get(ActivityHomeGeneralMvvm.class);
        activityHomeGeneralMvvm.getLocation().observe(activity, userSettingModel -> {
            tvTitle.setText(userSettingModel.getCountryModel().getTitel() + "-" + userSettingModel.getCityModel().getTitel());
        });

        llLocation.setOnClickListener(v -> {
            req = 1;
            Intent intent = new Intent(activity, MapActivity.class);
            intent.putExtra("from","home");
            launcher.launch(intent);
        });

        rootDestination.add(R.id.fragmentHomeCatering);
        rootDestination.add(R.id.fragmentOffer);
        rootDestination.add(R.id.fragmentCart);
        rootDestination.add(R.id.fragmentOrder);
        rootDestination.add(R.id.fragmentProfile);

        appBarConfiguration = new AppBarConfiguration.Builder(rootDestination)
                .build();
        navController = Navigation.findNavController(activity, navHostId);
        Toolbar toolbar = root.findViewById(toolBarId);

        toolbar.setTitleTextColor(ContextCompat.getColor(activity, R.color.white));
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
    }


    public boolean onBackPressed() {
        return navController.navigateUp();
    }
}
