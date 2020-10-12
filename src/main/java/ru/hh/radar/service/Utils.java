package ru.hh.radar.service;

import org.jsoup.Jsoup;

public class Utils {
    public static String htmlToText(String html) {
        return Jsoup.parse(html).text();
    }
}
