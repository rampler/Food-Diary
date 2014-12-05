package com.example.medicineApp.helpers;

import android.text.TextUtils;

/**
 * Created by Sabina on 2014-12-05.
 */
public class Validator {

    public static boolean isEmpty(CharSequence target) {
         return TextUtils.isEmpty(target);
    }

    public static boolean isCorrectEmail(CharSequence target) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static boolean isEqual(CharSequence a, CharSequence b) {
        return a.toString().equals(b.toString());
    }

}
