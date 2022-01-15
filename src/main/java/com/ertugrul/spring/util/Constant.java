package com.ertugrul.spring.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Constant {
    public static Double getLateFeeRate(Date date) {
        try {
            return date.after(new SimpleDateFormat("dd.MM.yyyy").parse("1.1.2018")) ? 2 : 1.5;
        } catch (ParseException e) {
            return 1.0;
        }
    }

    public static int monthsBetween(Date d1, Date d2) {
        if (d2 == null || d1 == null) {
            return -1;
        }
        Calendar m_calendar = Calendar.getInstance();
        m_calendar.setTime(d1);
        int nMonth1 = 12 * m_calendar.get(Calendar.YEAR) + m_calendar.get(Calendar.MONTH);
        m_calendar.setTime(d2);
        int nMonth2 = 12 * m_calendar.get(Calendar.YEAR) + m_calendar.get(Calendar.MONTH);
        return java.lang.Math.abs(nMonth2 - nMonth1);
    }
}
