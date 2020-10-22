package ru.hh.radar.service.hh;

import ru.hh.radar.dto.HhUserDTO;
import ru.hh.radar.model.entity.User;


public interface HhUserService {
    /**
     * Получение информации об авторизованном пользователе
     * https://github.com/hhru/api/blob/master/docs/me.md#user-info
     *
     * @param user пользователь
     * @return вернёт информацию о текущем авторизованном пользователе
     */
    HhUserDTO getHhUserInfo(User user);
}
