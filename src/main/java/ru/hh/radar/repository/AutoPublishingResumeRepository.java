package ru.hh.radar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.hh.radar.model.entity.AutoPublishingResume;

import java.time.LocalDateTime;
import java.util.List;

public interface AutoPublishingResumeRepository extends JpaRepository<AutoPublishingResume, Long> {

    AutoPublishingResume findByResume(String resumeId);

    boolean existsByResume(String resumeId);

    @Query("select a from AutoPublishingResume a where a.lastUpdatedTime <= :nowMinusPublishingDelay")
    List<AutoPublishingResume> findAllAvailableByTime(@Param("nowMinusPublishingDelay") LocalDateTime nowMinusPublishingDelay);
}
