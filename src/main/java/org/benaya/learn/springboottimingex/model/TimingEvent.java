package org.benaya.learn.springboottimingex.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TimingEvent {
    @JsonProperty("event")
    private String type;
    private String data;

}
