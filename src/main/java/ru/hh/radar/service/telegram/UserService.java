package ru.hh.radar.service.telegram;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.model.User;

public interface UserService {

    User createUser(Update update) throws TelegramApiException;

    User updateUser(User user);

    User findUser(Update update) throws TelegramApiException;
}
