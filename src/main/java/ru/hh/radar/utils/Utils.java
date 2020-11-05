package ru.hh.radar.utils;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Locale;

@Slf4j
public class Utils {
    public static String htmlToText(String html) {
        String text = Jsoup.parse(html).text();
        return deleteExtraSymbols(text);
    }

    public static String deleteExtraSymbols(String str) {
        return str.replaceAll("&quot;", "");
    }

    /**
     * Форматирование даты
     * @param sourceData LocalDateTime
     * @return string data. example = "2020-10-16 18:04"
     */
    public static String getFormattingData(LocalDateTime sourceData) {
        DateTimeFormatter newFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm", Locale.ENGLISH);
        return newFormatter.format(sourceData);
    }

    /**
     * Форматирование даты
     * @param sourceData string data. example = 2020-10-16T18:04:59+0300
     * @return LocalDateTime
     */
    public static LocalDateTime getLocalDateTime(String sourceData) {
        DateTimeFormatter sourceFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH);
        return LocalDateTime.parse(sourceData, sourceFormatter);
    }

    /**
     * Возвращает ключа команды: from '/start 123' returns '/start'
     * @param command сообщение пользователя
     * @return ключ
     */
    public static String getCommandKey(String command) {
        if (command == null) return null;
        if (!isClickableCommand(command)) return command;
        return (command.contains(" ")) ? command.split(" ")[0] : command;
    }

    /**
     * Возвращает значение команды: from '/start 123' returns '123'
     * @param command сообщение пользователя
     * @return ключ
     */
    public static String getCommandValue(String command) {
        if (command == null) return null;

        String commandKey = getCommandKey(command);
        if (commandKey == null) return null;

        String commandValue = command.replaceFirst(commandKey, "");
        return (commandValue.length() > 0) ? commandValue.trim() : null;
    }

    /**
     * Проверяет, что команда имеет ключ (принимает, что ключ должен начинаться с символа '/'
     * @param command сообщение пользователя
     * @return boolean
     */
    public static boolean isClickableCommand(String command){
        return command.startsWith("/");
    }

    /**
     * Шифрует строку
     * @param originalInput исходное значение
     * @return зашифрованное значение
     */
    public static String encode(String originalInput) {
        return Base64.getEncoder().encodeToString(originalInput.getBytes());
    }

    /**
     * Расшифровывает строку
     * @param encodedString зашифрованное значение
     * @return исходное значение
     */
    public static String decode(String encodedString) {
        return new String(Base64.getDecoder().decode(encodedString));
    }
}
