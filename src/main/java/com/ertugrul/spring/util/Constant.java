package com.ertugrul.spring.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Constant {
    public static Double getLateFeeRate(Date date){
        try {
            return date.after(new SimpleDateFormat("dd.MM.yyyy").parse("1.1.2018")) ? 2 : 1.5;
        } catch (ParseException e) {
            return 1.0;
        }
    }
}
