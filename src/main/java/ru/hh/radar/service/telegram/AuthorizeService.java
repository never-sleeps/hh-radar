package ru.hh.radar.service.telegram;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.hh.radar.model.entity.User;

public interface AuthorizeService {

    SendMessage showAuthorizeButton(User user, String lang);
}
