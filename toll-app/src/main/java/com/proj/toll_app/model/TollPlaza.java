package com.proj.toll_app.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TollPlaza {
    private String name;
    private double latitude;
    private double longitude;
}