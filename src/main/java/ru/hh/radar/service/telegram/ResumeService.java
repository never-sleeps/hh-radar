package ru.hh.radar.service.telegram;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.hh.radar.model.entity.User;

import java.util.List;

public interface ResumeService {
    List<SendMessage> showAllResume(User user, String lang);

    SendMessage showPublishResumeMenu(User user, String lang);

    SendMessage publishResume(User user, String resumeId, String lang);
}
