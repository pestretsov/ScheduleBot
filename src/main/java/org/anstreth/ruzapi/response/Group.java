package org.anstreth.ruzapi.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Group {
    private int id;
    private String name;
    private String spec;
}
