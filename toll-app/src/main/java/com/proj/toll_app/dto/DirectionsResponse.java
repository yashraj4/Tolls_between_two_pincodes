package com.proj.toll_app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DirectionsResponse {
    private List<Route> routes;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Route {
        private OverviewPolyline overview_polyline;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OverviewPolyline {
        private String points;
    }
}