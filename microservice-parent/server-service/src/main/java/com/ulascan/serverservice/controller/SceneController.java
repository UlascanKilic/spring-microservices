package com.ulascan.serverservice.controller;

import com.ulascan.serverservice.dto.SceneResponseDTO;
import com.ulascan.serverservice.dto.StartSceneDTO;
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

    @PostMapping
    public ResponseEntity<Void> startScene(@RequestBody StartSceneDTO startSceneDTO){

        sceneService.startScene(startSceneDTO);

        return ResponseEntity.ok().build();
    }

    //TODO login endpointi yap. input olarak sceneName ve password alacak. Eğer bilgiler uyuşuyorsa 200, uyuşmuyorsa bad request

    //todo get scenes by scene type (only active ones)

}
