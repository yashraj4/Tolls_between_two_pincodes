package com.proj.toll_app.service;

import com.proj.toll_app.data.TollPlazaRepository;
import com.proj.toll_app.dto.RouteResponseDto;
import com.proj.toll_app.dto.TollPlazaDto;
import com.proj.toll_app.dto.TollRequestDto;
import com.proj.toll_app.model.TollPlaza;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TollService {

    private final TollPlazaRepository tollPlazaRepository;
    private final MapServiceClient mapServiceClient;

    public TollService(TollPlazaRepository tollPlazaRepository, MapServiceClient mapServiceClient) {
        this.tollPlazaRepository = tollPlazaRepository;
        this.mapServiceClient = mapServiceClient;
    }

    @Cacheable(value = "tollRoutes", key = "#requestDto.sourcePincode + '-' + #requestDto.destinationPincode")
    public RouteResponseDto findTollsOnRoute(TollRequestDto requestDto) {
        if (requestDto.getSourcePincode().equals(requestDto.getDestinationPincode())) {
            throw new IllegalArgumentException("Source and destination pincodes cannot be the same");
        }

        // Get coordinates using the service client
        MapServiceClient.Coordinates sourceCoords = mapServiceClient.getCoordinatesForPincode(requestDto.getSourcePincode());
        MapServiceClient.Coordinates destCoords = mapServiceClient.getCoordinatesForPincode(requestDto.getDestinationPincode());

        if (sourceCoords == null || destCoords == null) {
            throw new IllegalArgumentException("Invalid source or destination pincode");
        }

        // Get the route polyline from the service client
        List<MapServiceClient.Coordinates> routePolyline = mapServiceClient.getRoutePolyline(sourceCoords, destCoords);
        double totalDistance = calculateHaversineDistance(sourceCoords, destCoords);

        // System.out.println("Route Polyline Coordinates: " + routePolyline); 

        // Filter tolls using an accurate proximity check
        List<TollPlaza> allTolls = tollPlazaRepository.findAll();
        List<TollPlazaDto> tollsOnRoute = allTolls.stream()
                .filter(toll -> isTollNearPolyline(routePolyline, new MapServiceClient.Coordinates(toll.getLatitude(), toll.getLongitude()), 0.5)) // 0.5 km threshold
                .map(toll -> {
                    TollPlazaDto dto = new TollPlazaDto();
                    dto.setName(toll.getName());
                    dto.setLatitude(toll.getLatitude());
                    dto.setLongitude(toll.getLongitude());
                    double dist = calculateHaversineDistance(sourceCoords, new MapServiceClient.Coordinates(toll.getLatitude(), toll.getLongitude()));
                    dto.setDistanceFromSource(Math.round(dist * 100.0) / 100.0);
                    return dto;
                })
                .sorted(Comparator.comparing(TollPlazaDto::getDistanceFromSource))
                .collect(Collectors.toList());

        RouteResponseDto response = new RouteResponseDto();
        response.setSourcePincode(requestDto.getSourcePincode());
        response.setDestinationPincode(requestDto.getDestinationPincode());
        response.setDistanceInKm(Math.round(totalDistance * 100.0) / 100.0);
        response.setTollPlazas(tollsOnRoute);

        return response;
    }

    private boolean isTollNearPolyline(List<MapServiceClient.Coordinates> polyline, MapServiceClient.Coordinates toll, double thresholdKm) {
        if (polyline.isEmpty()) {
            return false;
        }
        for (int i = 0; i < polyline.size() - 1; i++) {
            MapServiceClient.Coordinates start = polyline.get(i);
            MapServiceClient.Coordinates end = polyline.get(i + 1);
            
            double distStartToll = calculateHaversineDistance(start, toll);
            double distTollEnd = calculateHaversineDistance(toll, end);
            double distSegment = calculateHaversineDistance(start, end);

            if (Math.abs((distStartToll + distTollEnd) - distSegment) < thresholdKm) {
                return true;
            }
        }
        return false;
    }

    private double calculateHaversineDistance(MapServiceClient.Coordinates c1, MapServiceClient.Coordinates c2) {
        final int R = 6371; // Radius of the Earth (in km)
        double latDistance = Math.toRadians(c2.lat() - c1.lat());
        double lonDistance = Math.toRadians(c2.lon() - c1.lon());
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(c1.lat())) * Math.cos(Math.toRadians(c2.lat()))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}