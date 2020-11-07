package ru.hh.radar.service.hh;

import ru.hh.radar.model.TelegramUserInfo;
import ru.hh.radar.model.entity.User;

import java.net.URI;

/**
 * Сервис авторизации пользователя
 * https://github.com/hhru/api/blob/master/docs/authorization_for_user.md
 */
public interface HhOauthService {

    /**
     * Получение ссылки для авторизации пользователя
     * https://github.com/hhru/api/blob/master/docs/authorization_for_user.md#get-auth
     * @return ссылка для авторизации пользователя
     */
    URI getUserAuthorizeURI();

    /**
     * Авторизация пользователя, получение и сохранение access и refresh токенов для него
     * https://github.com/hhru/api/blob/master/docs/authorization_for_user.md#получение-access-и-refresh-токенов
     * @param userInfo - данные телеграм пользователя
     * @param command - полный текст команды
     * @return пользователь
     */
    User authorizeUser(TelegramUserInfo userInfo, String command);
}
