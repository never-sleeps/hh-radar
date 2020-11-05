package ru.hh.radar.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "AUTO_PUBLISHING_VACANCIES")
public class AutoPublishingResume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "resume")
    private String resume;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "last_updated_time")
    private LocalDateTime lastUpdatedTime;

    @Column(name = "publish_count")
    private Long publishCount = 0L;

    @ToString.Exclude
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

    public void setLastUpdatedTime(LocalDateTime lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
        this.publishCount++;
    }
}
