package com.ulascan.serverservice.controller;

import com.ulascan.serverservice.dto.*;
import com.ulascan.serverservice.entity.Scene;
import com.ulascan.serverservice.service.SceneService;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<SceneResponseDTO>> getScenesByUser(@Valid @RequestBody SceneByUserRequestDTO sceneByUserRequestDTO){
        return ResponseEntity.ok(sceneService.getScenesByUser(sceneByUserRequestDTO));
    }

    @PostMapping
        public ResponseEntity<StartSceneResponseDTO> startScene(@Valid @RequestBody SceneRequestDTO sceneRequestDTO){
        return ResponseEntity.ok(sceneService.startScene(sceneRequestDTO));
    }
    @PostMapping("/join")
    public ResponseEntity<Void> joinScene(@Valid @RequestBody JoinSceneDTO joinSceneDTO)
    {
        sceneService.joinScene(joinSceneDTO);

        return ResponseEntity.ok().build();
    }
    @PostMapping("/type")
    public ResponseEntity<List<SceneResponseDTO>> getActiveScenesByType(@Valid @RequestBody
                                                                            SceneByTypeRequestDTO sceneByTypeRequestDTO){
        return ResponseEntity.ok(sceneService.getActiveScenesByType(sceneByTypeRequestDTO));
    }

    @PostMapping("/unityScene")
    public ResponseEntity<List<SceneResponseDTO>> getActiveScenesByUnityName(@Valid @RequestBody
                                                                                 SceneByUnityNameRequestDTO sceneByUnityNameRequestDTO){
        return ResponseEntity.ok(sceneService.getActiveScenesByUnityName(sceneByUnityNameRequestDTO));
    }

    @DeleteMapping("/close/{serverName}")
    public ResponseEntity<Void> deleteSceneByServerName(@PathVariable(value = "serverName") String serverName) {
        sceneService.deleteSceneByServerName(serverName);
        return ResponseEntity.ok().build();
    }



}
