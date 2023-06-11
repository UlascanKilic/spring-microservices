package com.ulascan.serverservice.controller;

import com.ulascan.serverservice.dto.scene.*;
import com.ulascan.serverservice.dto.scene.session.CreateSessionResponseDTO;
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

    @DeleteMapping
    public ResponseEntity<Void> deleteSceneBySceneName(@Valid @RequestBody NameDTO nameDTO) {
        sceneService.delete(nameDTO);
        return ResponseEntity.ok().build();
    }
   /* @GetMapping
    public ResponseEntity<List<SceneResponseDTO>> getAllScenes() {
        return ResponseEntity.ok(sceneService.getAllScenes());
    }

    @PostMapping
        public ResponseEntity<CreateSessionResponseDTO> startScene(@Valid @RequestBody SceneRequestDTO sceneRequestDTO){
        return ResponseEntity.ok(sceneService.startScene(sceneRequestDTO));
    }
    @PostMapping("/join")
    public ResponseEntity<Void> joinScene(@Valid @RequestBody JoinSceneDTO joinSceneDTO)
    {
        sceneService.joinScene(joinSceneDTO);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/unityScene")
    public ResponseEntity<List<SceneResponseDTO>> getActiveScenesByUnityName(@Valid @RequestBody
                                                                             SceneByUnityNameRequestDTO sceneByUnityNameRequestDTO){
        return ResponseEntity.ok(sceneService.getActiveScenesByUnityName(sceneByUnityNameRequestDTO));
    }


    }*/



}
