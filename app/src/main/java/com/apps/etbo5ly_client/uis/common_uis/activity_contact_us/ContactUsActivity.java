package com.apps.etbo5ly_client.uis.common_uis.activity_contact_us;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.Toast;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.databinding.ActivityContactUsBinding;
import com.apps.etbo5ly_client.model.ContactUsModel;
import com.apps.etbo5ly_client.model.UserModel;
import com.apps.etbo5ly_client.mvvm.ContactusActivityMvvm;
import com.apps.etbo5ly_client.common.preferences.Preferences;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseActivity;

public class ContactUsActivity extends BaseActivity {
    private ActivityContactUsBinding binding;
    private ContactUsModel contactUsModel;
    private ContactusActivityMvvm contactusActivityMvvm;
    private UserModel userModel;
    private Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact_us);
        initView();

    }

    private void initView() {
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        contactusActivityMvvm = ViewModelProviders.of(this).get(ContactusActivityMvvm.class);
        setUpToolbar(binding.toolbar, getString(R.string.contact_us), R.color.white, R.color.black);

        contactUsModel = new ContactUsModel();
        if (userModel != null) {
            contactUsModel.setName(userModel.getData().getName());

        }

        binding.setContactModel(contactUsModel);
        binding.btnSend.setOnClickListener(view -> {
            if (contactUsModel.isDataValid(this)) {
                contactusActivityMvvm.contactus(this, contactUsModel);
            }
        });
        contactusActivityMvvm.send.observe(this, aBoolean -> {
            if (aBoolean) {
                Toast.makeText(ContactUsActivity.this, getResources().getString(R.string.suc), Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }


}