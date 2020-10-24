package ru.hh.radar.service.common;

import ru.hh.radar.model.entity.User;

public interface UserService {

    User save(String userName, String command);

    User save(User user);

    User findUser(String userName);
}
