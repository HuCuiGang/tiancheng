package com.yufan.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String source) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            Date date = df.parse(source);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
