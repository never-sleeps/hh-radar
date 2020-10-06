package ru.hh.radar.service.hh;

import ru.hh.radar.model.User;

/**
 * Сервис авторизации пользователя
 * https://github.com/hhru/api/blob/master/docs/authorization_for_user.md
 */
public interface HhOauthService {

    /**
     * Получение авторизации пользователя
     * https://github.com/hhru/api/blob/master/docs/authorization_for_user.md#get-auth
     * @param user пользователь
     */
    void authorizationForUser(User user);

    /**
     * Обновление пары access и refresh токенов
     * https://github.com/hhru/api/blob/master/docs/authorization_for_user.md#refresh_token
     * @param user пользователь
     */
    void refreshToken(User user);

    /**
     * Проверка access-токена
     * https://github.com/hhru/api/blob/master/docs/authorization.md#check-access_token
     * @param user пользователь
     */
    boolean accessTokenIsValid(User user);
}
