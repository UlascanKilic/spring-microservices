package com.ulascan.serverservice.controller;

import com.ulascan.serverservice.dto.scene.CreateResponseDTO;
import com.ulascan.serverservice.dto.scene.SceneResponseDTO;
import com.ulascan.serverservice.dto.scene.environment.EnvironmentResponseDTO;
import com.ulascan.serverservice.dto.scene.event.*;
import com.ulascan.serverservice.dto.scene.session.SessionRequestDTO;
import com.ulascan.serverservice.service.event.EventSceneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scene/event")
@RequiredArgsConstructor
public class EventController {

    private final EventSceneService eventService;

    @GetMapping
    public ResponseEntity<List<SceneResponseDTO>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAll());
    }
    @PostMapping
    public ResponseEntity<CreateResponseDTO> createEvent(@Valid @RequestBody EventRequestDTO eventRequestDTO)
    {
        return ResponseEntity.ok(eventService.create(eventRequestDTO));
    }

    @PostMapping("/name")
    public ResponseEntity<SceneResponseDTO> getEventByName(@Valid @RequestBody EventNameDTO eventName)
    {
        return ResponseEntity.ok(eventService.getByName(eventName));
    }

    @PostMapping("/password")
    public ResponseEntity<SceneResponseDTO> joinEventWithPassword(@Valid @RequestBody EventPasswordDTO password){
        return ResponseEntity.ok(eventService.joinWithPassword(password));
    }

    @PostMapping("/start")
    public ResponseEntity<Void> startEvent(@Valid @RequestBody EventNameDTO eventName)
    {
        eventService.startEvent(eventName);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/stop")
    public ResponseEntity<Void> stopEvent(@Valid @RequestBody EventNameDTO eventName)
    {
        eventService.stopEvent(eventName);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/join")
    public ResponseEntity<Void> joinEvent(@Valid @RequestBody JoinEventDTO joinEventDTO)
    {
        eventService.join(joinEventDTO);
        return ResponseEntity.ok().build();
    }
}
