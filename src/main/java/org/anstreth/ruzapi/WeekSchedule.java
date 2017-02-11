package org.anstreth.ruzapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeekSchedule {
    private Group group;
    private List<Day> days;
}
