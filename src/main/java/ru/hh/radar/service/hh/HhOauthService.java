package ru.hh.radar.service.hh;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
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
     *
     * @param update запрос из бота
     * @return пользователь
     * @throws TelegramApiException
     */
    User authorizeUser(Update update) throws TelegramApiException;


//
//    /**
//     * Обновление пары access и refresh токенов
//     * https://github.com/hhru/api/blob/master/docs/authorization_for_user.md#refresh_token
//     * @param user пользователь
//     */
//    void refreshToken(User user);
//
//    /**
//     * Проверка access-токена
//     * https://github.com/hhru/api/blob/master/docs/authorization.md#check-access_token
//     * @param user пользователь
//     */
//    boolean accessTokenIsValid(User user);
}
