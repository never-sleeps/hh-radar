package ru.hh.radar.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Locale;

@Slf4j
public class Utils {
    public static String htmlToText(String html) {
        return Jsoup.parse(html).text();
    }

    /**
     * Форматирование даты
     * @param sourceData string data. example = 2020-10-16T18:04:59+0300
     * @return string data. example = "2020-10-16 18:04"
     */
    public static String getFormattingData(String sourceData) {
        DateTimeFormatter sourceFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH);
        DateTimeFormatter newFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm", Locale.ENGLISH);
        LocalDateTime date = LocalDateTime.parse(sourceData, sourceFormatter);
        return newFormatter.format(date);
    }

    public static String getCommandKey(String command) {
        if (command == null) return null;
        if (!isClickableCommand(command)) return command;
        return (command.contains(" ")) ? command.split(" ")[0] : command;
    }

    public static String getCommandValue(String command) {
        if (command == null) return null;

        String commandKey = getCommandKey(command);
        if (commandKey == null) return null;

        String commandValue = command.replaceFirst(commandKey, "");
        return (commandValue.length() > 0) ? commandValue : null;
    }

    public static boolean isClickableCommand(String command){
        return command.startsWith("/");
    }

    public static String encode(String originalInput) {
        return Base64.getEncoder().encodeToString(originalInput.getBytes());
    }

    public static String decode(String encodedString) {
        return new String(Base64.getDecoder().decode(encodedString));
    }
}
