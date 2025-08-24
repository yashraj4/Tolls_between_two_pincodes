package com.proj.toll_app.dto; 

import lombok.Data;
import java.util.List;

@Data
public class RouteResponseDto {
    private String sourcePincode;
    private String destinationPincode;
    private double distanceInKm;
    private List<TollPlazaDto> tollPlazas;
}