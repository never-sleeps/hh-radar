package ru.hh.radar.service.common.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.hh.radar.model.SearchParametersType;
import ru.hh.radar.model.entity.SearchParameters;
import ru.hh.radar.model.entity.User;
import ru.hh.radar.repository.UserRepository;
import ru.hh.radar.service.common.UserService;
import ru.hh.radar.utils.Utils;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User save(String userName, String command) {
        User user = userRepository.findByUsername(userName);
        if (user == null) {
            user = User.builder()
                    .username(userName)
                    .searchParameters(new SearchParameters())
                    .build();
        }
        user.setAuthorizationCode(Utils.getCommandValue(command));
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
    public User findUser(String userName) {
        User user = userRepository.findByUsername(userName);
        if (user == null) {
            log.error("User not found: " + userName);
        }
        return user;
    }

    @Override
    public User saveSearchParameter(User user, SearchParametersType searchParam, String value) {
        SearchParameters searchParameters = user.getSearchParameters();
        if (SearchParametersType.AREA == searchParam) {
            searchParameters.setArea(value);
        }
        if (SearchParametersType.SPECIALIZATION == searchParam) {
            searchParameters.setSpecialization(value);
        }
        if (SearchParametersType.TEXT == searchParam) {
            searchParameters.setText(value);
        }
        if (SearchParametersType.EXPERIENCE == searchParam) {
            searchParameters.setExperience(value);
        }
        if (SearchParametersType.EXPERIENCE == searchParam) {
            searchParameters.setExperience(value);
        }
        if (SearchParametersType.EMPLOYMENT == searchParam) {
            searchParameters.setEmployment(value);
        }
        if (SearchParametersType.SCHEDULE == searchParam) {
            searchParameters.setSchedule(value);
        }
        user.setSearchParameters(searchParameters);
        return save(user);
    }

    @Override
    public User cleanSearchParameters(User user) {
        user.setSearchParameters(new SearchParameters());
        return save(user);
    }
}
