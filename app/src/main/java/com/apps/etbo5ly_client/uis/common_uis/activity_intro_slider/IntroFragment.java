package com.apps.etbo5ly_client.uis.common_uis.activity_intro_slider;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.databinding.FragmentIntroBinding;
import com.apps.etbo5ly_client.model.UserSettingsModel;
import com.apps.etbo5ly_client.uis.common_uis.activity_base.BaseFragment;

public class IntroFragment extends BaseFragment {
    private FragmentIntroBinding binding;
    private String title, content;
    private int imageResource;
    private boolean showBtn;
    private IntroSliderActivity activity;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (IntroSliderActivity) context;
    }

    public static IntroFragment newInstance(int imageResource, String title, String content, boolean showBtn) {
        IntroFragment fragment = new IntroFragment();
        Bundle args = new Bundle();
        args.putInt("img", imageResource);
        args.putString("title", title);
        args.putString("content", content);
        args.putBoolean("showBtn", showBtn);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageResource = getArguments().getInt("img");
            title = getArguments().getString("title");
            content = getArguments().getString("content");
            showBtn = getArguments().getBoolean("showBtn");

        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_intro, container, false);
        initView();

        return binding.getRoot();
    }

    private void initView() {
        binding.image.setImageResource(imageResource);
        binding.tvTitle.setText(title);
        binding.tvContent.setText(content);

        if (showBtn) {
            binding.btnStart.setVisibility(View.VISIBLE);
        } else {
            binding.btnStart.setVisibility(View.INVISIBLE);

        }
        binding.btnStart.setOnClickListener(v -> navigateToLoginActivity());
    }

    private void navigateToLoginActivity() {

        UserSettingsModel model = getUserSettings();
        model.setFirstTime(false);
        setUserSettings(model);
        activity.setResult(Activity.RESULT_OK);
        activity.finish();
    }
}