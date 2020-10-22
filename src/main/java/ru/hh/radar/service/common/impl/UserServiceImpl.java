package ru.hh.radar.service.common.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hh.radar.model.entity.User;
import ru.hh.radar.repository.UserRepository;
import ru.hh.radar.service.Utils;
import ru.hh.radar.service.common.UserService;
import ru.hh.radar.telegram.service.TelegramService;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TelegramService telegramService;

    @Override
    public User save(Update update) throws TelegramApiException {
        String userName = telegramService.getMessage(update).getFrom().getUserName();

        User user = userRepository.findByUsername(userName);
        if (user == null) {
            user = User.builder().username(userName).build();
        }

        user.setChatId(telegramService.getMessage(update).getChatId());
        user.setAuthorizationCode(
                Utils.getCommandValue(telegramService.getCommand(update))
        );
        user = userRepository.save(user);
        log.info("User saved: " + user);
        return user;
    }

    @Override
    public User save(User user) {
        User savedUser = userRepository.save(user);
        log.info("User saved: " + savedUser);
        return savedUser;
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
    public String getLanguageCode(Update update) throws TelegramApiException {
        return telegramService.getFrom(update).getLanguageCode();
    }
}
