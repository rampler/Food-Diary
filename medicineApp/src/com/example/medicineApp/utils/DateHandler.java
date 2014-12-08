package com.example.medicineApp.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Sabina on 2014-12-08.
 */
public class DateHandler {

    public static String GetTodaysDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        String dateToday = formatDate.format(c.getTime());
        return  dateToday;
    }
}
