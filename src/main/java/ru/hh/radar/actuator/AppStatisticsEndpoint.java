package ru.hh.radar.actuator;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;
import ru.hh.radar.model.entity.User;
import ru.hh.radar.service.common.AutoPublishingResumeService;
import ru.hh.radar.service.common.UserService;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Endpoint(id = "application-statistics")
public class AppStatisticsEndpoint {

    private final UserService userService;
    private final AutoPublishingResumeService autoPublishingResumeService;

    @ReadOperation
    public AppStatistics getAppUsersCount() {
        List<String> userNames = new ArrayList<>();
        long authorizedUsersCount = 0L;
        for (User user: userService.findAll()) {
            userNames.add(user.getUsername());
            if(user.isAuthorized()) authorizedUsersCount++;
        }
        return AppStatistics.builder()
                .usersCount(userService.count())
                .authorizedUsersCount(authorizedUsersCount)
                .autoPublishingResumeCount(autoPublishingResumeService.count())
                .users(userNames)
                .build()
                ;
    }

    @Data
    @Builder
    public static class AppStatistics {
        @JsonProperty("Количество пользователей")
        private long usersCount;

        @JsonProperty("Количество авторизованных пользователей")
        private long authorizedUsersCount;

        @JsonProperty("Количество резюме с включенным автообновлением")
        private long autoPublishingResumeCount;

        @JsonProperty("Пользователи")
        private List<String> users;
    }
}
