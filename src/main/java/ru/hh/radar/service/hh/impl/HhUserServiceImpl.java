package ru.hh.radar.service.hh.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.hh.radar.client.hh.HhUserClient;
import ru.hh.radar.dto.HhUserDTO;
import ru.hh.radar.model.entity.User;
import ru.hh.radar.service.hh.HhUserService;

@Slf4j
@RequiredArgsConstructor
@Service
public class HhUserServiceImpl implements HhUserService {

    private final HhUserClient hhUserClient;

    @Override
    public HhUserDTO getHhUserInfo(User user) {
        HhUserDTO authorizedUser = hhUserClient.getHhUserInfo(user);
        log.info(String.format("%s: Authorized user: %s", user.getUsername(), authorizedUser));
        return authorizedUser;
    }
}
