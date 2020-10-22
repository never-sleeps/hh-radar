package ru.hh.radar.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 *
 * {
 *     "description": "Can't publish resume: too often",
 *     "errors": [
 *         {
 *             "value": "touch_limit_exceeded",
 *             "type": "resumes"
 *         }
 *     ],
 *     "request_id": "1603361440729d2b9ba4f263f55cf251"
 * }
 */
@Data
public class ErrorDTO {
    @JsonProperty(value = "description")
    private String description;

    @Override
    public String toString() {
        return (description != null) ? description : "";
    }
}
