package ru.hh.radar.service.common;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.model.entity.User;

public interface UserService {

    User save(Update update) throws TelegramApiException;

    User save(User user) throws TelegramApiException;

    User findUser(Update update) throws TelegramApiException;

    String getLanguageCode(Update update) throws TelegramApiException;
}
