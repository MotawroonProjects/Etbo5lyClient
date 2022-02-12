package com.apps.etbo5ly_client.model;

import android.content.Context;
import android.util.Patterns;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.apps.etbo5ly_client.BR;
import com.apps.etbo5ly_client.R;


public class ContactUsModel extends BaseObservable {
    private String name;
    private String email;
    private String subject;
    private String message;
    private boolean isValid ;
    private Context context;
    public ObservableField<String> error_name = new ObservableField<>();
    public ObservableField<String> error_email = new ObservableField<>();
    public ObservableField<String> error_subject = new ObservableField<>();
    public ObservableField<String> error_message = new ObservableField<>();



    public void isDataValid(Context context) {

        if (!name.isEmpty() &&
                !email.isEmpty() &&
                Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
                !subject.isEmpty() &&
                !message.isEmpty()

        ) {


            error_name.set(null);
            error_email.set(null);
            error_subject.set(null);
            error_message.set(null);


           setValid(true);

        } else {

            if (name.isEmpty()) {
                error_name.set(context.getString(R.string.field_required));
            } else {
                error_name.set(null);

            }


            if (email.isEmpty()) {
                error_email.set(context.getString(R.string.field_required));
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                error_email.set(context.getString(R.string.inv_email));
            } else {
                error_email.set(null);

            }

            if (subject.isEmpty()) {
                error_subject.set(context.getString(R.string.field_required));
            } else {
                error_subject.set(null);

            }

            if (message.isEmpty()) {
                error_message.set(context.getString(R.string.field_required));
            } else {
                error_message.set(null);

            }

            setValid(false);


        }

    }

    public ContactUsModel() {
        name = "";
        email = "";
        subject = "";
        message = "";
        isValid = false;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
        isDataValid(getContext());
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
        isDataValid(getContext());


    }

    @Bindable
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
        notifyPropertyChanged(BR.subject);
        isDataValid(getContext());


    }

    @Bindable
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        notifyPropertyChanged(BR.message);
        isDataValid(getContext());


    }

    @Bindable
    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
        notifyPropertyChanged(BR.valid);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
