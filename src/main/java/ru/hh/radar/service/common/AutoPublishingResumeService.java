package ru.hh.radar.service.common;

import ru.hh.radar.dto.ResumeDTO;
import ru.hh.radar.model.entity.AutoPublishingResume;
import ru.hh.radar.model.entity.User;

import java.util.List;

public interface AutoPublishingResumeService {

    boolean isAutoPublishingResume(String resumeId);

    AutoPublishingResume enableAutoPublishing(ResumeDTO resume, User user);

    void disableAutoPublishing(ResumeDTO resume, User user);

    List<AutoPublishingResume> getAvailableForUpdatingResumes();

    AutoPublishingResume save(AutoPublishingResume autoPublishingResume);

    long count();
}
