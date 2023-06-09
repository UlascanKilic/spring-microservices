package com.ulascan.serverservice.controller;

import com.ulascan.serverservice.dto.scene.environment.EnvironmentResponseDTO;
import com.ulascan.serverservice.service.environment.EnvironmentSceneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/scene/environment")
@RequiredArgsConstructor
public class EnvironmentController {

    private final EnvironmentSceneService environmentService;

    @GetMapping
    public ResponseEntity<List<EnvironmentResponseDTO>> getAllEnvironments() {
        return ResponseEntity.ok(environmentService.getAllEnvironments());
    }
}
