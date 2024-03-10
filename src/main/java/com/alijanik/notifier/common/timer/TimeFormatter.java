package com.alijanik.notifier.common.timer;

import java.text.SimpleDateFormat;
import java.util.*;

public class TimeFormatter {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final SimpleDateFormat formatterHour = new SimpleDateFormat("HH");

    public static String getTimeString() {
        return formatter.format(new Date());
    }

    public static int getCurrentHour() {
        String currentHourString = formatterHour.format(new Date());
        return Integer.parseInt(currentHourString);
    }
}
