package com.proj.toll_app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class TollRequestDto {
    @NotBlank(message = "Source pincode is mandatory")
    @Pattern(regexp = "^\\d{6}$", message = "Invalid source pincode format")
    private String sourcePincode;

    @NotBlank(message = "Destination pincode is mandatory")
    @Pattern(regexp = "^\\d{6}$", message = "Invalid destination pincode format")
    private String destinationPincode;
}