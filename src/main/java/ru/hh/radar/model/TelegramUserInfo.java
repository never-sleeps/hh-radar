package ru.hh.radar.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TelegramUserInfo {
    private Long userId;
    private String userName;

    @Override
    public String toString() {
        return "userId =" + userId +
                ", userName='" + userName + '\'';
    }
}
