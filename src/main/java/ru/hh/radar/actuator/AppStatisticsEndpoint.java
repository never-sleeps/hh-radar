package ru.hh.radar.actuator;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;
import ru.hh.radar.model.entity.AutoPublishingResume;
import ru.hh.radar.model.entity.User;
import ru.hh.radar.repository.AutoPublishingResumeRepository;
import ru.hh.radar.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Endpoint(id = "application-statistics")
public class AppStatisticsEndpoint {

    private final UserRepository userRepository;
    private final AutoPublishingResumeRepository autoPublishingResumeRepository;

    @ReadOperation
    public AppStatistics getAppUsersCount() {
        return AppStatistics.builder()
                .usersCount(userRepository.count())
                .authorizedUsersCount(getAuthorizedUsersCount())
                .autoPublishingResumeCount(autoPublishingResumeRepository.count())
                .autoPublishingResumeInfo(getAutoPublishingResumes())
                .build();
    }

    private long getAuthorizedUsersCount() {
        long authorizedUsersCount = 0L;
        for (User user: userRepository.findAll()) {
            if(user.isAuthorized()) authorizedUsersCount++;
        }
        return authorizedUsersCount;
    }

    private List<AutoPublishingResumeInfo> getAutoPublishingResumes() {
        List<AutoPublishingResumeInfo> resumes = new ArrayList<>();
        for (AutoPublishingResume resume: autoPublishingResumeRepository.findAll()) {
            resumes.add(
                    AutoPublishingResumeInfo.builder()
                            .user(resume.getUser().getUserId())
                            .resume(resume.getResume())
                            .publishingCount(resume.getPublishCount())
                            .build()
            );
        }
        return resumes;
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

        @JsonProperty("Резюме с включенным автообновлением")
        private List<AutoPublishingResumeInfo> autoPublishingResumeInfo;
    }

    @Data
    @Builder
    public static class AutoPublishingResumeInfo {
        @JsonProperty("Пользователь")
        private Long user;

        @JsonProperty("Резюме")
        private String resume;

        @JsonProperty("Число автоматических обновлений")
        private long publishingCount;
    }
}
