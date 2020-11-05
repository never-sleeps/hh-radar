package ru.hh.radar.service.common;

import ru.hh.radar.model.SearchParametersType;
import ru.hh.radar.model.entity.User;

import java.util.List;

public interface UserService {

    User save(String userName, String command);

    User save(User user);

    User findUser(String userName);

    User saveSearchParameter(User user, SearchParametersType searchParam, String value);

    User cleanSearchParameters(User user);

    long count();

    List<User> findAll();
}
