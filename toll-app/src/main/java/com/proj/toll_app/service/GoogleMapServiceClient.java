package com.proj.toll_app.service;

import com.proj.toll_app.dto.DirectionsResponse;
import com.proj.toll_app.dto.GeocodingResponse;
import com.proj.toll_app.util.PolylineDecoder; // We will create this utility next
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

@Service
public class GoogleMapServiceClient implements MapServiceClient {

    private final RestTemplate restTemplate;

    @Value("${google.api.key}")
    private String apiKey;

    private static final String GEOCODING_API_URL = "https://maps.googleapis.com/maps/api/geocode/json";
    private static final String DIRECTIONS_API_URL = "https://maps.googleapis.com/maps/api/directions/json";

    public GoogleMapServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Coordinates getCoordinatesForPincode(String pincode) {
        // Build the URL for the Directions API
        String url = UriComponentsBuilder.fromUriString(GEOCODING_API_URL)
                .queryParam("address", pincode + ", India") // Added "+, India" for better accuracy
                .queryParam("key", apiKey)
                .toUriString();

        // API call
        GeocodingResponse response = restTemplate.getForObject(url, GeocodingResponse.class);

        if (response != null && response.getResults() != null && !response.getResults().isEmpty()) {
            GeocodingResponse.Location location = response.getResults().get(0).getGeometry().getLocation();
            return new Coordinates(location.getLat(), location.getLng());
        }

        return null;
    }

    @Override
    public List<Coordinates> getRoutePolyline(Coordinates start, Coordinates end) {
        // Build the URL for the Directions API
        String url = UriComponentsBuilder.fromUriString(DIRECTIONS_API_URL)
                .queryParam("origin", start.lat() + "," + start.lon())
                .queryParam("destination", end.lat() + "," + end.lon())
                .queryParam("key", apiKey)
                .toUriString();

        DirectionsResponse response = restTemplate.getForObject(url, DirectionsResponse.class);

        // Parsing the response, getting the encoded polyline, and decode it
        if (response != null && response.getRoutes() != null && !response.getRoutes().isEmpty()) {
            String encodedPolyline = response.getRoutes().get(0).getOverview_polyline().getPoints();
            return PolylineDecoder.decode(encodedPolyline);
        }

        return Collections.emptyList(); // no route found
    }
}