package org.anstreth.ruzapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Day {
    private String date;
    private List<Lesson> lessons;
}
