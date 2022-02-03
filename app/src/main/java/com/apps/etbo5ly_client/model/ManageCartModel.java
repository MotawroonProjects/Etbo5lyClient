package com.apps.etbo5ly_client.model;

import android.content.Context;
import android.os.Bundle;
import android.preference.Preference;
import android.util.Log;
import android.widget.Toast;

import com.apps.etbo5ly_client.R;
import com.apps.etbo5ly_client.common.preferences.Preferences;

import java.io.Serializable;
import java.util.List;

public class ManageCartModel implements Serializable {
    public static ManageCartModel instance;

    public static synchronized ManageCartModel newInstance() {
        if (instance == null) {
            instance = new ManageCartModel();
        }

        return instance;
    }

    private ManageCartModel() {
    }

    public void addItemToCart(Context context, SendOrderModel.Details item, String kitchen_id) {
        SendOrderModel model = getSendOrderModel(context);
        if (!model.getCaterer_id().isEmpty() && !model.getCaterer_id().equals(kitchen_id)) {
            Toast.makeText(context, R.string.order_only_place, Toast.LENGTH_SHORT).show();
            return;
        } else {
            model.setCaterer_id(kitchen_id);
        }

        List<SendOrderModel.Details> details = model.getDetails();
        if (details.size() > 0) {
            String item_id = "";
            if (item.getDishes_id() != null && !item.getDishes_id().isEmpty()) {
                item_id = item.getDishes_id();
            } else if (item.getBuffets_id() != null && !item.getBuffets_id().isEmpty()) {
                item_id = item.getBuffets_id();
            } else if (item.getFeast_id() != null && !item.getFeast_id().isEmpty()) {
                item_id = item.getFeast_id();
            } else if (item.getOffer_id() != null && !item.getOffer_id().isEmpty()) {
                item_id = item.getOffer_id();
            }

            int pos = getItemPos(item_id, model.getDetails());

            if (pos == -1) {


                details.add(item);


            } else {
                SendOrderModel.Details itemModel = details.get(pos);
                int newAmount = Integer.parseInt(item.getQty());

                itemModel.setQty(newAmount + "");
                details.set(pos, itemModel);


            }
        } else {
            details.add(item);

        }
        model.setDetails(details);
        setSendOrder(context, model);

    }

    private int getItemPos(String item_id, List<SendOrderModel.Details> details) {
        int pos = -1;
        for (int index = 0; index < details.size(); index++) {
            SendOrderModel.Details model = details.get(index);
            if (model.getDishes_id().equals(item_id)) {
                pos = index;
                return pos;
            } else if (model.getBuffets_id().equals(item_id)) {
                pos = index;
                return pos;
            } else if (model.getFeast_id().equals(item_id)) {
                pos = index;
                return pos;
            } else if (model.getOffer_id().equals(item_id)) {
                pos = index;
                return pos;
            }
        }

        return pos;
    }

    public double getTotal(Context context) {
        SendOrderModel model = getSendOrderModel(context);
        List<SendOrderModel.Details> details = model.getDetails();
        double total = 0;
        for (SendOrderModel.Details item : details) {
            total += Double.parseDouble(item.getPrice()) * Integer.parseInt(item.getQty());
        }

        return total;
    }

    public void removeItem(Context context, String item_id) {
        SendOrderModel model = getSendOrderModel(context);
        List<SendOrderModel.Details> details = model.getDetails();
        int pos = getItemPos(item_id, model.getDetails());
        if (pos != -1) {
            details.remove(pos);
        }
        if (details.size() == 0) {
            model.setCaterer_id("");
            model = null;
        } else {
            model.setDetails(details);

        }
        setSendOrder(context, model);


    }

    public void clearCart(Context context) {
        setSendOrder(context, null);


    }

    public SendOrderModel getSendOrderModel(Context context) {
        Preferences preferences = Preferences.getInstance();
        SendOrderModel cart = preferences.getCart(context);
        if (cart == null) {
            cart = new SendOrderModel();
        }
        return cart;
    }

    private void setSendOrder(Context context, SendOrderModel model) {
        Preferences preferences = Preferences.getInstance();
        preferences.create_update_cart(context, model);


    }

    public List<SendOrderModel.Details> getDishesList(Context context) {
        return getSendOrderModel(context).getDetails();
    }
}
