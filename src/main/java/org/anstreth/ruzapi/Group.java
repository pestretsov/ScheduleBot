package org.anstreth.ruzapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
class Group {
    private String name;
    private String spec;
}
