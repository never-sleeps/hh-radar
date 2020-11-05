package ru.hh.radar.service.common.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
    @DisplayName("должен находить пользоватея по userName")
    void shouldFindUser() {
        // given
        String userName = "test";
        User user = User.builder()
                .username(userName)
                .build();
        given(userRepository.findByUsername(userName)).willReturn(user);
        // when
        User foundUser = userService.findUser(userName);
        // then
        verify(userRepository, times(1)).findByUsername(userName);
        Assertions.assertEquals(user.getUsername(), foundUser.getUsername());
    }

    @Test
    @DisplayName("должен создавать нового пользователя")
    void shouldSaveUserByUserName() {
        // given
        String userName = "test";
        String command = "/start code";
        User user = User.builder()
                .username(userName)
                .authorizationCode("code")
                .build();
        given(userRepository.findByUsername(userName)).willReturn(null);
        given(userRepository.save(any())).willReturn(user);

        // when
        User createdUser = userService.save(userName, command);

        // then
        Assertions.assertEquals(user.getUsername(), createdUser.getUsername());
        Assertions.assertEquals(user.getAuthorizationCode(), createdUser.getAuthorizationCode());
    }
}