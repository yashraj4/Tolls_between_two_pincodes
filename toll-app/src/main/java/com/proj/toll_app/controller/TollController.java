package com.proj.toll_app.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.proj.toll_app.dto.TollRequestDto;
import com.proj.toll_app.dto.RouteResponseDto;
import com.proj.toll_app.service.TollService;

@RestController
@RequestMapping("/api/v1")
public class TollController {

    private final TollService tollService;

    public TollController(TollService tollService) {
        this.tollService = tollService;
    }

    @PostMapping("/toll-plazas")
    public ResponseEntity<RouteResponseDto> getTollPlazas(@Valid @RequestBody TollRequestDto requestDto) {
        RouteResponseDto response = tollService.findTollsOnRoute(requestDto);
        return ResponseEntity.ok(response);
    }
}