package ru.hh.radar.service.common.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.radar.dto.ResumeDTO;
import ru.hh.radar.model.entity.AutoPublishingResume;
import ru.hh.radar.model.entity.User;
import ru.hh.radar.repository.AutoPublishingResumeRepository;
import ru.hh.radar.service.common.AutoPublishingResumeService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AutoPublishingResumeServiceImpl implements AutoPublishingResumeService {

    private final AutoPublishingResumeRepository repository;

    @Value("${headhunter.timeBetweenPublishing.hours}")
    private int timeBetweenPublishingInHours;

    @Transactional(readOnly = true)
    @Override
    public List<AutoPublishingResume> getAvailableForUpdatingResumes() {
        return repository.findAllAvailableByTime(LocalDateTime.now().plusHours(3).minusHours(timeBetweenPublishingInHours));
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isAutoPublishingResume(String resumeId) {
        return repository.existsByResume(resumeId);
    }

    @Override
    public AutoPublishingResume enableAutoPublishing(ResumeDTO resume, User user) {
        AutoPublishingResume autoPublishingResume = AutoPublishingResume.builder()
                .resume(resume.getId())
                .lastUpdatedTime(resume.getUpdatedAt())
                .user(user)
                .createdTime(LocalDateTime.now())
                .build();
        autoPublishingResume =  repository.save(autoPublishingResume);
        log.info(String.format("%s: Auto-publishing enabled for resume: %s", user.getUsername(), resume.getId()));
        return autoPublishingResume;
    }

    @Override
    public void disableAutoPublishing(ResumeDTO resume, User user) {
        AutoPublishingResume autoPublishingResume = repository.findByResume(resume.getId());
        repository.delete(autoPublishingResume);
        log.info(String.format("%s: Auto-publishing disiabled for resume: %s", user.getUsername(), resume.getId()));
    }

    @Override
    public AutoPublishingResume save(AutoPublishingResume autoPublishingResume) {
        AutoPublishingResume saved = repository.save(autoPublishingResume);
        log.info("Auto-publishing resume saved: " + saved);
        return repository.save(autoPublishingResume);
    }
}
