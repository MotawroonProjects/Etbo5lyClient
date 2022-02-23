package com.apps.etbo5ly_client.common.general_ui;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;


import com.apps.etbo5ly_client.R;

import com.apps.etbo5ly_client.common.tags.Tags;
import com.apps.etbo5ly_client.model.NotificationModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;

public class GeneralMethod {

    @BindingAdapter("error")
    public static void errorValidation(View view, String error) {
        if (view instanceof EditText) {
            EditText ed = (EditText) view;
            ed.setError(error);
        } else if (view instanceof TextView) {
            TextView tv = (TextView) view;
            tv.setError(error);


        }
    }

    @BindingAdapter("image")
    public static void image(View view, String imageUrl) {
        if (imageUrl != null) {
            view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);


                    if (view instanceof CircleImageView) {
                        CircleImageView imageView = (CircleImageView) view;
                        RequestOptions options = new RequestOptions().override(view.getWidth(), view.getHeight());
                        Glide.with(view.getContext()).asBitmap()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .load(Tags.base_url + imageUrl)
                                .centerCrop()
                                .apply(options)
                                .into(imageView);
                    } else if (view instanceof RoundedImageView) {
                        RoundedImageView imageView = (RoundedImageView) view;
                        RequestOptions options = new RequestOptions().override(view.getWidth(), view.getHeight());
                        Glide.with(view.getContext()).asBitmap()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .load(Tags.base_url + imageUrl)
                                .centerCrop()
                                .apply(options)
                                .into(imageView);
                    } else if (view instanceof ImageView) {
                        ImageView imageView = (ImageView) view;

                        RequestOptions options = new RequestOptions().override(view.getWidth(), view.getHeight());
                        Glide.with(view.getContext()).asBitmap()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .load(Tags.base_url + imageUrl)
                                .centerCrop()
                                .apply(options)
                                .into(imageView);
                    }

                }
            });

        }


    }

    @BindingAdapter("user_image")
    public static void user_image(View view, String imageUrl) {
        if (view instanceof CircleImageView) {
            CircleImageView imageView = (CircleImageView) view;
            if (imageUrl != null) {

                Glide.with(view.getContext()).asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.circle_avatar)
                        .load(Uri.parse(Tags.base_url + imageUrl))
                        .centerCrop()
                        .into(imageView);

            }
        } else if (view instanceof RoundedImageView) {
            RoundedImageView imageView = (RoundedImageView) view;

            if (imageUrl != null) {

                Glide.with(view.getContext()).asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.circle_avatar)
                        .load(Uri.parse(Tags.base_url + imageUrl))
                        .centerCrop()
                        .into(imageView);

            }
        } else if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;

            if (imageUrl != null) {

                Glide.with(view.getContext()).asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.circle_avatar)
                        .load(Uri.parse(Tags.base_url + imageUrl))
                        .centerCrop()
                        .into(imageView);
            }
        }

    }


    @BindingAdapter("createAt")
    public static void dateCreateAt(TextView textView, String s) {
        if (s != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            try {
                Date date = simpleDateFormat.parse(s);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd\nhh:mm aa", Locale.ENGLISH);
                dateFormat.setTimeZone(TimeZone.getDefault());
                String d = dateFormat.format(date);
                textView.setText(d);
            } catch (ParseException e) {
                e.printStackTrace();
            }


        }

    }

    @BindingAdapter("createAtMsg")
    public static void dateCreateAtMsg(TextView textView, String s) {
        if (s != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            try {
                Date date = simpleDateFormat.parse(s);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm aa", Locale.ENGLISH);
                dateFormat.setTimeZone(TimeZone.getDefault());
                String d = dateFormat.format(date);
                textView.setText(d);
            } catch (ParseException e) {
                e.printStackTrace();
            }


        }

    }


    @BindingAdapter("providerType")
    public static void providerType(TextView textView, String type) {
        if (type != null) {
            if (type.equals("man")) {
                textView.setText(R.string.men);
            } else if (type.equals("women")) {
                textView.setText(R.string.women);

            } else if (type.equals("man_and_women")) {
                textView.setText(R.string.men_women);

            } else if (type.equals("not_found")) {
                textView.setText(R.string.undefined);

            }
        }


    }

    @BindingAdapter("notification")
    public static void notification(TextView textView, NotificationModel model) {
        if (model != null) {
            Context context = textView.getContext();
            String text = "";
            if (model.getOrder_id() != null && !model.getOrder_id().isEmpty()) {
                if (model.getBody().equals("approval")) {
                    text = context.getString(R.string.order_approved) + " " + model.getCaterer_name() + "\n" + context.getString(R.string.order_num) + " #" + model.getOrder_id();
                } else if (model.getBody().equals("refusal")) {
                    text = context.getString(R.string.order_refused) + " " + model.getCaterer_name() + "\n" + context.getString(R.string.order_num) + " #" + model.getOrder_id();

                } else if (model.getBody().equals("making")) {
                    text = context.getString(R.string.your_order_from) + " " + model.getCaterer_name() + " " + context.getString(R.string.order_num) + " #" + model.getOrder_id() + "\n" + context.getString(R.string.status_pending);

                } else if (model.getBody().equals("delivery")) {
                    text = context.getString(R.string.your_order_from) + " " + model.getCaterer_name() + " " + context.getString(R.string.order_num) + " #" + model.getOrder_id() + "\n" + context.getString(R.string.delivering);

                } else if (model.getBody().equals("completed")) {
                    text = context.getString(R.string.your_order_from) + " " + model.getCaterer_name() + " " + context.getString(R.string.order_num) + " #" + model.getOrder_id() + "\n" + context.getString(R.string.delivered2);

                } else {
                    text = model.getBody();

                }

                textView.setText(text);

            } else {
                textView.setText(model.getBody());

            }
        }

    }


}










