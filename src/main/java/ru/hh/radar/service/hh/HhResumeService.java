package ru.hh.radar.service.hh;

import ru.hh.radar.dto.ResumeResultsDTO;
import ru.hh.radar.model.entity.User;

public interface HhResumeService {

    ResumeResultsDTO getHhUserResume(User user);
}
