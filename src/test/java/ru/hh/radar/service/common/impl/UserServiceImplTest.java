package ru.hh.radar.service.common.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.hh.radar.model.TelegramUserInfo;
import ru.hh.radar.model.entity.User;
import ru.hh.radar.repository.UserRepository;
import ru.hh.radar.service.common.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Сервис пользователей")
@SpringBootTest(classes = UserServiceImpl.class)
class UserServiceImplTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("должен находить пользоватея по userId")
    void shouldFindUser() {
        // given
        Long userId = 1234L;
        User user = User.builder()
                .userId(userId)
                .build();
        given(userRepository.findByUserId(userId)).willReturn(user);
        // when
        User foundUser = userService.findUser(userId);
        // then
        verify(userRepository, times(1)).findByUserId(userId);
        Assertions.assertEquals(user.getUsername(), foundUser.getUsername());
    }

    @Test
    @DisplayName("должен создавать нового пользователя")
    void shouldSaveUserByUserName() {
        // given
        TelegramUserInfo userInfo = TelegramUserInfo.builder()
                .userId(12345L)
                .userName("tester")
                .build();
        String command = "/start code";
        User user = User.builder()
                .userId(userInfo.getUserId())
                .username(userInfo.getUserName())
                .authorizationCode("code")
                .build();
        given(userRepository.findByUserId(userInfo.getUserId())).willReturn(null);
        given(userRepository.save(any())).willReturn(user);

        // when
        User createdUser = userService.save(userInfo, command);

        // then
        Assertions.assertEquals(user.getUsername(), createdUser.getUsername());
        Assertions.assertEquals(user.getAuthorizationCode(), createdUser.getAuthorizationCode());
    }
}