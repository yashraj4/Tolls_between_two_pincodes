package com.proj.toll_app.dto;
import lombok.Data;

@Data
public class TollPlazaDto {
    private String name;
    private double latitude;
    private double longitude;
    private double distanceFromSource;
}