package com.ulascan.serverservice.controller;
import com.ulascan.serverservice.dto.scene.CreateResponseDTO;
import com.ulascan.serverservice.dto.scene.SceneResponseDTO;
import com.ulascan.serverservice.dto.scene.session.*;
import com.ulascan.serverservice.service.session.SessionSceneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scene/session")
@RequiredArgsConstructor
public class SessionController {

    private final SessionSceneService sessionSceneService;

    @GetMapping
    public ResponseEntity<List<SceneResponseDTO>> getAllSessions() {
        return ResponseEntity.ok(sessionSceneService.getAll());
    }

    @PostMapping
    public ResponseEntity<CreateResponseDTO> createSession(@Valid @RequestBody SessionRequestDTO sessionRequestDTO)
    {
        return ResponseEntity.ok(sessionSceneService.create(sessionRequestDTO));
    }

    @PostMapping("/name")
    public ResponseEntity<SceneResponseDTO> getSessionByName(@Valid @RequestBody SessionNameDTO sessionName)
    {
        return ResponseEntity.ok(sessionSceneService.getByName(sessionName));
    }

    @PostMapping("/password")
    public ResponseEntity<SceneResponseDTO> joinWithPassword(@Valid @RequestBody SessionPasswordDTO password)
    {
        return ResponseEntity.ok(sessionSceneService.joinWithPassword(password));
    }
    @PostMapping("/join")
    public ResponseEntity<Void> joinSession(@Valid @RequestBody JoinSessionDTO joinSessionDTO)
    {
        sessionSceneService.join(joinSessionDTO);
        return ResponseEntity.ok().build();
    }
}
