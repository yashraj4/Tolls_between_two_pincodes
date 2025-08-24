package com.proj.toll_app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeocodingResponse {
    private List<GeocodingResult> results;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GeocodingResult {
        private Geometry geometry;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Geometry {
        private Location location;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Location {
        private double lat;
        private double lng;
    }
}