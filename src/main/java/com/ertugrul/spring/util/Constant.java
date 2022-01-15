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

    public static double calculateLateFee(double amount, Date expirationDate) {
        double totalLateFee = 0.0;
        Calendar cStart = Calendar.getInstance();
        cStart.setTime(expirationDate);
        Calendar cEnd = Calendar.getInstance();
        cEnd.setTime(new Date());

        while (cStart.before(cEnd)) {
            double lateFee = amount * getLateFeeRate(expirationDate) / 100 ;
            if (lateFee < 1.0)
                lateFee = 1.0;
            totalLateFee += lateFee;
            cStart.add(Calendar.DAY_OF_MONTH, 1);
        }
        return Math.round(totalLateFee * 100.0) / 100.0;
    }
}
