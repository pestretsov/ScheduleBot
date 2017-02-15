package org.anstreth.ruzapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LessonType {
    private String name;

    @JsonProperty("abbr")
    private String shortName;
}
