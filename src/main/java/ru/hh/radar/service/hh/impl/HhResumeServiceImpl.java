package ru.hh.radar.service.hh.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.hh.radar.client.hh.HhResumeClient;
import ru.hh.radar.dto.ResumeDTO;
import ru.hh.radar.dto.ResumeStatusDTO;
import ru.hh.radar.dto.ResumesResultsDTO;
import ru.hh.radar.model.entity.AutoPublishingResume;
import ru.hh.radar.model.entity.User;
import ru.hh.radar.service.common.AutoPublishingResumeService;
import ru.hh.radar.service.hh.HhResumeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class HhResumeServiceImpl implements HhResumeService {

    private final HhResumeClient hhResumeClient;
    private final AutoPublishingResumeService autoPublishingResumeService;

    @Override
    public List<ResumeDTO> getAllResume(User user) {
        ResumesResultsDTO results = hhResumeClient.getAllResume(user);
        log.info(String.format("%s: %s resumes received", user.getUsername(), results.getFound()));
        return results.getItems();
    }

    @Override
    public Map<ResumeDTO, ResumeStatusDTO> getAllResumeInfo(User user) {
        Map<ResumeDTO, ResumeStatusDTO> resumeMap = new HashMap<>();
        for (ResumeDTO resume: getAllResume(user)) {
            resumeMap.put(resume, getStatusResume(resume.getId(), user));
        }
        return resumeMap;
    }

    @Override
    public ResumeDTO getResume(String resumeId, User user) {
        ResumeDTO resume = hhResumeClient.getResume(resumeId, user);
        log.info(String.format("%s: got resume %s", user.getUsername(), resumeId));
        return resume;
    }

    @Override
    public ResumeStatusDTO getStatusResume(String resumeId, User user) {
        ResumeStatusDTO results = hhResumeClient.getStatusResume(resumeId, user);
        log.info(String.format("%s: got status resume %s", user.getUsername(), resumeId));
        return results;
    }

    @Override
    public boolean publishResume(String resumeId, User user) {
        boolean is2xxSuccessful = hhResumeClient.publishResume(resumeId, user);
        if(is2xxSuccessful) {
            log.info(String.format("%s: published resume %s", user.getUsername(), resumeId));
        }
        return is2xxSuccessful;
    }

    @Scheduled(fixedDelay = 30 * 60 * 1000) // раз в 30 минут
    public void autoPublishResume() {
        List<AutoPublishingResume> list = autoPublishingResumeService.getAvailableForUpdatingResumes();
        long successful = 0L; long error = 0L;

        for (AutoPublishingResume autoPublish : list) {
            boolean isSuccessful = publishResume(autoPublish.getResume(), autoPublish.getUser());
            if (isSuccessful) {
                ResumeDTO resume = getResume(autoPublish.getResume(), autoPublish.getUser());
                autoPublish.setLastUpdatedTime(resume.getUpdatedAt());
                autoPublishingResumeService.save(autoPublish);
                successful++;
            }
            else error++;
        }
        log.info(String.format("Got for auto-publish: %s. successful: %s. error: %s", list.size(), successful, error));
    }
}
