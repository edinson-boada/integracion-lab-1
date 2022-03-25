package com.unmsm.sistemas.integracion.isg5.utils;

import java.util.Calendar;

public class CalendarUtils {
    public static int getDaysQuantityFromDate(int year, int month) {
        Calendar date = Calendar.getInstance();
        date.set(year, month, 0);
        return date.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}
