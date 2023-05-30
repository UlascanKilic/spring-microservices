package com.ulascan.serverservice.controller;

import com.ulascan.serverservice.dto.*;
import com.ulascan.serverservice.service.SceneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scene")
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
    public ResponseEntity<StartSceneResponseDTO> startScene(@RequestBody SceneRequestDTO sceneRequestDTO){
        return ResponseEntity.ok(sceneService.startScene(sceneRequestDTO));
    }
    @PostMapping("/join")
    public ResponseEntity<Void> joinScene(@RequestBody JoinSceneDTO joinSceneDTO)
    {
        sceneService.joinScene(joinSceneDTO);

        return ResponseEntity.ok().build();
    }
    @PostMapping("/type")
    public ResponseEntity<List<SceneResponseDTO>> getActiveScenesByType(@RequestBody SceneByTypeRequestDTO sceneByTypeRequestDTO){
        return ResponseEntity.ok(sceneService.getActiveScenesByType(sceneByTypeRequestDTO));
    }

    @DeleteMapping("/close/{serverName}")
    public ResponseEntity<Void> deleteSceneByServerName(@PathVariable(value = "serverName") String serverName) {
        sceneService.deleteSceneByServerName(serverName);
        return ResponseEntity.ok().build();
    }



}
