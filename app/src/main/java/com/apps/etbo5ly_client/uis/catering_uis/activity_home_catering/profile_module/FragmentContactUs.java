package com.apps.etbo5ly_client.uis.catering_uis.activity_home_catering.profile_module;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.common.share.Common;
import com.apps.etbo5ly_client.databinding.FragmentContactUsBinding;
import com.apps.etbo5ly_client.databinding.FragmentProfileBinding;
import com.apps.etbo5ly_client.model.ContactUsModel;
import com.apps.etbo5ly_client.mvvm.mvvm_catering.ActivityHomeGeneralMvvm;
import com.apps.etbo5ly_client.mvvm.mvvm_common.FragmentContactUsMvvm;
import com.apps.etbo5ly_client.uis.catering_uis.activity_home_catering.HomeActivity;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseFragment;


public class FragmentContactUs extends BaseFragment {
    private FragmentContactUsBinding binding;
    private HomeActivity activity;
    private ContactUsModel model;
    private FragmentContactUsMvvm mvvm;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact_us, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        mvvm = ViewModelProviders.of(this).get(FragmentContactUsMvvm.class);
        mvvm.onDataSuccess().observe(activity, isSuccess -> {
            Toast.makeText(activity, R.string.suc, Toast.LENGTH_SHORT).show();
            Navigation.findNavController(activity, R.id.fragmentBaseProfile).navigateUp();
        });
        model = new ContactUsModel();
        model.setContext(activity);
        if (getUserModel() != null) {
            model.setName(getUserModel().getData().getName());
            model.setEmail(getUserModel().getData().getEmail());
        }

        binding.setModel(model);


        binding.btnSend.setOnClickListener(v -> {
            Common.CloseKeyBoard(activity, binding.edtName);
            mvvm.contactUs(model,activity);
        });
    }
}
