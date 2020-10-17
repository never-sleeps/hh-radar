package ru.hh.radar.service;

import org.jsoup.Jsoup;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Utils {
    public static String htmlToText(String html) {
        return Jsoup.parse(html).text();
    }

    // from "2020-10-16T18:04:59+0300" to "2020-10-16 18:04"
    public String getFormattingData(String sourceData) {
        DateTimeFormatter sourceFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ''e", Locale.ENGLISH);
        DateTimeFormatter newFormatter = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm", Locale.ENGLISH);
        LocalDate date = LocalDate.parse("2020-10-16T18:04:59+0300", sourceFormatter);
        return newFormatter.format(date);
    }
}
