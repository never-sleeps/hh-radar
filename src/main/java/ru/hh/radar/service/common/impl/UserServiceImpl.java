package ru.hh.radar.service.common.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.model.User;
import ru.hh.radar.repository.UserRepository;
import ru.hh.radar.service.common.UserService;
import ru.hh.radar.telegram.service.TelegramService;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TelegramService telegramService;

    @Override
    public User createUser(Update update) throws TelegramApiException {
        User existingUser = userRepository.findByUsername(telegramService.getMessage(update).getFrom().getUserName());
        if (existingUser != null) return existingUser;

        User savedUser = userRepository.save(
                new User(
                        telegramService.getMessage(update).getChatId(),
                        telegramService.getMessage(update).getFrom().getUserName()
                )
        );
        log.info("User created: " + savedUser);
        return savedUser;
    }

    @Override
    public User updateUser(User user) {
        User updatedUser = userRepository.save(user);
        log.info("User updated: " + updatedUser);
        return updatedUser;
    }

    @Override
    public User findUser(Update update) throws TelegramApiException {
        String userName = telegramService.getMessage(update).getChat().getUserName();
        User user = userRepository.findByUsername(userName);
        if (user == null) {
            log.error("User not found: " + userName);
        }
        return user;
    }

    @Override
    public String getLocaleForAnswerToUser(Update update) throws TelegramApiException {
        return telegramService.getFrom(update).getLanguageCode();
    }
}
