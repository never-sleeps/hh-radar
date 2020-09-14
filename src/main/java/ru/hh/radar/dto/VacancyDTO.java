package ru.hh.radar.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.hh.radar.dto.vacancy.*;

import java.util.List;

public class VacancyDTO {
    private String id;

    private String description;

    @JsonProperty(value = "branded_description")
    private String brandedDescription;

    @JsonProperty(value = "key_skills")
    private List<SkillDTO> skills;

    private ScheduleDTO schedule;

    private ExperienceDTO experience;

    private AddressDTO address;

    @JsonProperty(value = "alternate_url")
    private String alternateUrl;

    private EmploymentDTO employment;

    private SalaryDTO salary;

    private String name;

    @JsonProperty(value = "created_at")
    private String createdAt;

    @JsonProperty(value = "published_at")
    private String publishedAt;
}
