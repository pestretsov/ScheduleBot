package org.anstreth.ruzapi.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Day {
    @JsonProperty("weekday")
    private int weekDay;
    private String date;
    private List<Lesson> lessons;
}
