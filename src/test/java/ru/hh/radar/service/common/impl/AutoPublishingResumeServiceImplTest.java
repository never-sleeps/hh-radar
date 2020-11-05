package ru.hh.radar.service.common.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.hh.radar.dto.ResumeDTO;
import ru.hh.radar.model.entity.AutoPublishingResume;
import ru.hh.radar.model.entity.User;
import ru.hh.radar.repository.AutoPublishingResumeRepository;
import ru.hh.radar.service.common.AutoPublishingResumeService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Сервис автообновлений резюме")
@SpringBootTest(classes = AutoPublishingResumeServiceImpl.class)
class AutoPublishingResumeServiceImplTest {

    @MockBean
    private AutoPublishingResumeRepository repository;
    @Autowired
    private AutoPublishingResumeService autoPublishingResumeService;

    @Test
    @DisplayName("должен проверять является ли резюме автообновляемым")
    void shouldSheckIsAutoPublishingResume() {
        // given
        String resumeId = "1234";
        given(repository.existsByResume(resumeId)).willReturn(true);

        //when
        boolean isAutoPublishingResume = autoPublishingResumeService.isAutoPublishingResume(resumeId);

        // then
        Assertions.assertTrue(isAutoPublishingResume);
    }

    @Test
    @DisplayName("должен делать резюме автообновляемым")
    void shouldEnableAutoPublishing() {
        // given
        ResumeDTO resume = new ResumeDTO("1234");
        User user = User.builder().build();
        AutoPublishingResume autoPublishingResume = AutoPublishingResume.builder()
                .resume(resume.getId())
                .user(user)
                .build();
        given(repository.save(any())).willReturn(autoPublishingResume);

        // when
        AutoPublishingResume createdAutoPublishingResume = autoPublishingResumeService.enableAutoPublishing(resume, user);

        // then
        Assertions.assertEquals(autoPublishingResume.getResume(), createdAutoPublishingResume.getResume());
        Assertions.assertEquals(autoPublishingResume.getUser(), createdAutoPublishingResume.getUser());
    }

    @Test
    @DisplayName("должен отключать автообновление для резюме")
    void shouldDisableAutoPublishing() {
        // given
        ResumeDTO resume = new ResumeDTO("1234");
        User user = User.builder().build();
        AutoPublishingResume autoPublishingResume = AutoPublishingResume.builder()
                .resume(resume.getId())
                .user(user)
                .build();
        given(repository.findByResume(resume.getId())).willReturn(autoPublishingResume);

        // when
        autoPublishingResumeService.disableAutoPublishing(resume, user);

        // then
        verify(repository, times(1)).delete(autoPublishingResume);
    }
}