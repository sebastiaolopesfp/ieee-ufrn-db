package br.ufrn.ieee.database.evento.dto.vtools;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.Instant;

@Data
public class VToolsEventAttributesDTO {

    private String id;
    private String title;
    private String description;

    @JsonProperty("start-time")
    private Instant startTime;

    @JsonProperty("end-time")
    private Instant endTime;

    @JsonProperty("publish")
    private Boolean publish;

    @JsonProperty("virtual")
    private Boolean virtual;

    @JsonProperty("location_type")
    private String locationType;

    @JsonProperty("ieee-attending")
    private Integer ieeeAttending;

    @JsonProperty("guests-attending")
    private Integer guestsAttending;
}