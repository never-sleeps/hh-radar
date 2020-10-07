package ru.hh.radar.service.telegram.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.model.User;
import ru.hh.radar.repository.UserRepository;
import ru.hh.radar.service.telegram.UserService;
import ru.hh.radar.telegram.service.TelegramService;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TelegramService telegramService;

    @Override
    public User createUser(Update update) throws TelegramApiException {
        User user = new User(
                telegramService.getMessage(update).getChatId(),
                telegramService.getMessage(update).getFrom().getUserName()
        );
        log.info("Creating user: " + user);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        log.info("Updating user: " + user);
        return userRepository.save(user);
    }

    @Override
    public User findUser(Update update) throws TelegramApiException {
        String userName = telegramService.getMessage(update).getChat().getUserName();
        User user = userRepository.findByUsername(userName);
        log.info(String.format("search username: %s, found user: %s", userName, user));
        if (user == null) {
            log.error("User not found: " + userName);
        }
        return user;
    }
}
