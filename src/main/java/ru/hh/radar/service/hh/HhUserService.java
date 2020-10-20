package ru.hh.radar.service.hh;

import ru.hh.radar.dto.HhUserDTO;
import ru.hh.radar.model.entity.User;

public interface HhUserService {
    HhUserDTO getHhUserInfo(User user);
}
