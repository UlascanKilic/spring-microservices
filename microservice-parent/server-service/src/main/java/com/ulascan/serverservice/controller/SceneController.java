package com.ulascan.serverservice.controller;

import com.ulascan.serverservice.dto.*;
import com.ulascan.serverservice.enums.SceneType;
import com.ulascan.serverservice.service.SceneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/server/scene")
@RequiredArgsConstructor
public class SceneController {

    private final SceneService sceneService;

    @GetMapping
    public ResponseEntity<List<SceneResponseDTO>> getAllScenes() {
        return ResponseEntity.ok(sceneService.getAllScenes());
    }

    @PostMapping("/user")
    public ResponseEntity<List<SceneResponseDTO>> getScenesByUser(@RequestBody SceneByUserRequestDTO sceneByUserRequestDTO){
        return ResponseEntity.ok(sceneService.getScenesByUser(sceneByUserRequestDTO));
    }

    @PostMapping
    public ResponseEntity<Void> startScene(@RequestBody SceneRequestDTO sceneRequestDTO){

        sceneService.startScene(sceneRequestDTO);

        return ResponseEntity.ok().build();
    }
    @PostMapping("/login")
    public ResponseEntity<Void> loginScene(@RequestBody LoginSceneDTO loginSceneDTO)
    {
        sceneService.loginScene(loginSceneDTO);

        return ResponseEntity.ok().build();
    }
    @PostMapping("/type")
    public ResponseEntity<List<SceneResponseDTO>> getActiveScenesByType(@RequestBody SceneByTypeRequestDTO sceneByTypeRequestDTO){
        return ResponseEntity.ok(sceneService.getActiveScenesByType(sceneByTypeRequestDTO));
    }







}
