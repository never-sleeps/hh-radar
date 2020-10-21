package ru.hh.radar.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.http.HttpHeaders;
import ru.hh.radar.model.entity.User;

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
        return (commandValue.length() > 0) ? commandValue : null;
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

    public static HttpHeaders getAuthorizationHttpHeader(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + decode(user.getClientAccessToken().getAccessToken()));
        return headers;
    }
}
