package com.example.medicineApp.helpers;

import android.app.ProgressDialog;

/**
 * Created by Sabina on 2014-11-30.
 */
public class DialogControl {

    public static void showDialog(ProgressDialog pDialog) {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    public static void hideDialog(ProgressDialog pDialog) {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public static void configureNotCancellable(ProgressDialog pDialog) {
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
    }

}
